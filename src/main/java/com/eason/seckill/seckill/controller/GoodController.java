package com.eason.seckill.seckill.controller;

import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.service.GoodsService;
import com.eason.seckill.seckill.vo.GoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: eason
 * @Date: Created in 20:23 2020/2/23
 * @Description:
 */
@RestController
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/allGoods")
    public List<Good> getAllGoods(){
        return goodsService.getAllGoods();
    }

    @GetMapping("/list")
    public List<GoodVo> getAllSeckillGoods(){
        return goodsService.getAllSeckillGoods();
    }

    @GetMapping("/detail/{id}")
    public GoodVo detail(@PathVariable("id") long id){
        return goodsService.getGoodDetailById(id);
    }
}
