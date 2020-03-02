package com.eason.seckill.seckill.controller;

import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.service.UserService;
import com.eason.seckill.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/dologin")
    public Result doLogin(HttpServletResponse response, @Valid LoginVo loginVo){

        return userService.login(response, loginVo);
    }

    @GetMapping("/login")
    public String login(){

        return "index";
    }

    @GetMapping("/user")
    public String user(){

        return "user";
    }
}
