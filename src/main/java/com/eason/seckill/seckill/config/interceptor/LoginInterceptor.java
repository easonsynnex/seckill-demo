package com.eason.seckill.seckill.config.interceptor;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.UsersKey;
import com.eason.seckill.seckill.entity.User;
import com.eason.seckill.seckill.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.eason.seckill.seckill.config.redis.keys.UsersKey.TOKEN_EXPIRE;
import static com.eason.seckill.seckill.service.UserService.TOKEN_NAME;

/**
 * @Author: eason
 * @Date: Created in 20:18 2020/3/1
 * @Description: 登录校验
 */
@Service
public class LoginInterceptor extends HandlerInterceptorAdapter{
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断session是否存在，不存在则跳转到登录页面
        if(isLogin(request, response)){
            return true;
        }
        LOGGER.info("用户未登录,跳转到登录页面");
        request.getRequestDispatcher("/login/login").forward(request, response);
        return false;
    }

    /**
     * 判断当前用户是否已经登录
     * @param request
     * @param response
     * @return
     */
    private boolean isLogin(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (TOKEN_NAME.equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = redisService.get(UsersKey.TOKEN, token, User.class);
                    if (user != null) {
                        addCookie(response, token, user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void addCookie(HttpServletResponse response, String token,User user){
        //保存到redis,延长cookie有效期，更新session信息
        redisService.set(UsersKey.TOKEN, token, user);
        Cookie cookie = new Cookie(TOKEN_NAME, token);
        //cookie过期时间
        cookie.setMaxAge(TOKEN_EXPIRE);
        //将cookie返回页面
        response.addCookie(cookie);
    }
}
