package com.eason.seckill.seckill.controller;

import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.service.UserService;
import com.eason.seckill.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: eason
 * @Date: Created in 21:04 2020/2/27
 * @Description:
 */
@RestController
@RequestMapping("/login")
@Validated
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/doLogin")
    public Result doLogin(@Valid LoginVo loginVo){

        return userService.login(loginVo);
    }
}
