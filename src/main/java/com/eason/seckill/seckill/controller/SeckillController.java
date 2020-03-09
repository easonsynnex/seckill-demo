package com.eason.seckill.seckill.controller;

import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author: eason
 * @Date: Created in 20:22 2020/3/3
 * @Description: 秒杀相关接口
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    SeckillService seckillService;

    @PostMapping("/doSeckill")
    public Result doSeckill(HttpServletRequest request, @RequestParam("goodsId") long goodsId){
        return seckillService.doSeckill(request, goodsId);
    }
}
