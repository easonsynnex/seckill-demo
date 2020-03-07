package com.eason.seckill.seckill.service;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.UsersKey;
import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.entity.OrderInfo;
import com.eason.seckill.seckill.entity.SeckillGood;
import com.eason.seckill.seckill.entity.User;
import com.eason.seckill.seckill.exception.GlobalException;
import com.eason.seckill.seckill.vo.GoodVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

    @Transactional(rollbackFor = {Exception.class})
    public void doSeckill(HttpServletRequest request, long goodsId){
        User currentUser = getCurrentUser(request);
        //1.先判断库存
        GoodVo goodDetail = goodsService.getGoodDetailById(goodsId);
        if(goodDetail.getStockCount() <= 0){
            throw new GlobalException("商品库存不足");
        }
        //2.是否已秒杀过了
        boolean isSeckilled = seckillOrderService.isSeckilled(currentUser.getId(), goodsId);
        if(isSeckilled){
            throw new GlobalException("请勿重复秒杀");
        }
        //3.更新库存
        SeckillGood seckillGood = new SeckillGood();
        seckillGood.setGoodsId(goodsId);
        goodsService.subtractOneGood(seckillGood);

        //4.下订单
        createOrder(currentUser, goodsId);
    }

    /**
     * 下订单到order_info，再写入秒杀订单miaosha_order
     * @param currentUser
     * @param goodsId
     */
    private void createOrder(User currentUser, long goodsId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(goodsId);
        orderInfo.setUserId(currentUser.getId());
        orderInfo.setGoodsCount(1);
        orderInfo.set
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
