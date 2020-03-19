package com.eason.seckill.seckill.service;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.GoodsKey;
import com.eason.seckill.seckill.config.redis.keys.UsersKey;
import com.eason.seckill.seckill.entity.*;
import com.eason.seckill.seckill.exception.GlobalException;
import com.eason.seckill.seckill.kafka.KafkaProvider;
import com.eason.seckill.seckill.result.CodeMsg;
import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.vo.GoodVo;
import com.eason.seckill.seckill.vo.SeckillMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.ConcurrentHashMap;

import static com.eason.seckill.seckill.result.CodeMsg.*;
import static com.eason.seckill.seckill.service.UserService.TOKEN_NAME;

/**
 * @Author: eason
 * @Date: Created in 21:07 2020/3/3
 * @Description: 秒杀服务
 */
@Service
public class SeckillService {
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SeckillOrderService seckillOrderService;

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    KafkaProvider kafkaProvider;

    private ConcurrentHashMap<Long, Boolean> goodsOverFlag = new ConcurrentHashMap<>();

    @Transactional(rollbackFor = {Exception.class})
    public Result doSeckillToQueue(HttpServletRequest request, long goodsId){
        User currentUser = getCurrentUser(request);
        if(currentUser == null){
            return Result.error(USER_NOT_LOGIN);
        }
        //为了减少对redis的访问加上一个秒杀完的标记
        if(!goodsOverFlag.get(goodsId)){
            return Result.error(SECKILL_OVER);
        }
        //从缓存中预减库存
        Long stock = redisService.desc(GoodsKey.seckillGoodsCount, String.valueOf(goodsId));
        if(stock < 0){
            goodsOverFlag.put(goodsId, true);
            return Result.error(SECKILL_OVER);
        }

        /*//1.先判断库存
        GoodVo goodDetail = goodsService.getGoodDetailById(goodsId);
        if(goodDetail.getStockCount() <= 0){
            throw new GlobalException("商品库存不足");
        }*/
        //2.是否已秒杀过了
        boolean isSeckilled = seckillOrderService.isSeckilled(currentUser.getId(), goodsId);
        if(isSeckilled){
            redisService.incr(GoodsKey.seckillGoodsCount, String.valueOf(goodsId));
            throw new GlobalException(SECKILL_REPEAT);
        }
        //秒杀消息入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(currentUser);
        seckillMessage.setGoodsId(goodsId);
        kafkaProvider.send(seckillMessage);
        /*//3.更新库存
        SeckillGood seckillGood = new SeckillGood();
        seckillGood.setGoodsId(goodsId);
        goodsService.subtractOneGood(seckillGood);

        //4.下订单
        createOrder(currentUser, goodDetail);*/

        return Result.success("排队中");
    }

    @Transactional
    public Result<CodeMsg> doSeckillToDb(SeckillMessage message){
        Long goodsId = message.getGoodsId();
        //先更新库存
        SeckillGood seckillGood = new SeckillGood();
        seckillGood.setGoodsId(goodsId);
        boolean subtract = goodsService.subtractOneGood(seckillGood);
        if(!subtract){
            goodsOverFlag.put(goodsId, true);
            return Result.error(SECKILL_OVER);
        }
        //下订单
        GoodVo goodDetail = goodsService.getGoodDetailById(goodsId);
        createOrder(message.getUser(), goodDetail);

        return Result.success(SECKILL_SUCCESS);
    }

    /**
     * 下订单到order_info，再写入秒杀订单miaosha_order
     * @param currentUser
     * @param
     */
    private void createOrder(User currentUser, GoodVo good) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(good.getGoodsId());
        orderInfo.setUserId(currentUser.getId());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsName(good.getGoodsName());
        orderInfo.setOrderChannel(0);
        orderInfo.setGoodsPrice(good.getMiaoshaPrice());
        //orderInfo.setCreateDate(new Date());
        orderInfo.setStatus(0);
        //先下订单到order_info表
        int orderId = orderInfoService.saveOrderIndo(orderInfo);
        //再写到秒杀秒中
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(good.getGoodsId());
        seckillOrder.setUserId(currentUser.getId());
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrderService.saveSeckillOrder(seckillOrder);
    }

    /**
    * 获取当前登录用户
     * @param request
     * @return
     */
    public User getCurrentUser(HttpServletRequest request){
        String token = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (TOKEN_NAME.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        User currentUser = null;
        if(StringUtils.isNotEmpty(token)){
            //从缓存中获取用户信息
            currentUser = redisService.get(UsersKey.TOKEN, token, User.class);
        }

        return currentUser;
    }
}
