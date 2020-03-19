package com.eason.seckill.seckill.controller;

import com.eason.seckill.seckill.result.CodeMsg;
import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


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
    public Result doSeckill(HttpServletRequest request, @RequestBody Map param){
        if(param == null){
            return Result.error(CodeMsg.ARGUMENT_ERROR);
        }
        long goodsId = Long.parseLong(param.get("goodsId").toString());
        return seckillService.doSeckillToQueue(request, goodsId);
    }
}
