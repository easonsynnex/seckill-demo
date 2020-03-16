package com.eason.seckill.seckill.controller;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.GoodsKey;
import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.kafka.KafkaProvider;
import com.eason.seckill.seckill.service.GoodsService;
import com.eason.seckill.seckill.vo.GoodVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: eason
 * @Date: Created in 20:23 2020/2/23
 * @Description:
 */
@Controller
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    KafkaProvider kafkaProvider;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping(value = "/goods-list", produces = "text/html")
    @ResponseBody
    public String getAllGoods(HttpServletRequest request, HttpServletResponse response, Model model){
        List<Good> allGoods = goodsService.getAllGoods();
        model.addAttribute("goods_list", allGoods);
        String html = getHtmlCode("goods_list",model,GoodsKey.goodsListCache,request,response);

        return html;
    }



    @GetMapping(value = "/seckill-list", produces = "text/html")
    @ResponseBody
    public String getAllSeckillGoods(HttpServletRequest request, HttpServletResponse response, Model model){
        List<GoodVo> allSeckillGoods = goodsService.getAllSeckillGoods();
        model.addAttribute("seckill_list", allSeckillGoods);
        String html = getHtmlCode("seckill_list",model,GoodsKey.seckillGoodsListCache,request,response);

        return html;
    }

    /**
     * 商品详情页面
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/detail/{id}", produces = "text/html")
    @ResponseBody
    public String detail(@PathVariable("id") long id,HttpServletRequest request, HttpServletResponse response, Model model){
        GoodVo goodDetail = goodsService.getGoodDetailById(id);
        kafkaProvider.send(goodDetail);
        model.addAttribute("goods_detail", goodDetail);
        String html = getHtmlCode("goods_detail",model,GoodsKey.seckillGoodsDetailCache,request,response);

        return html;
    }

    /**
     * 获取html code，先从缓存中查找，没有则通过页面引擎渲染生成
     *
     */
    public String getHtmlCode(String path, Model model, GoodsKey goodsKey, HttpServletRequest request, HttpServletResponse response){
        //先从缓存中获取页面信息
        String html = redisService.get(goodsKey, "", String.class);
        if(StringUtils.isNotEmpty(html)){
            return html;
        }
        //通过thymel引擎渲染生成页面并缓存到redis
        WebContext ctx = new WebContext(request,response,request.getServletContext()
                ,request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process(path, ctx);
        if(StringUtils.isNotEmpty(html)){
            redisService.set(goodsKey,"", html);
        }

        return html;
    }
}
