package com.eason.seckill.seckill.service;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.UsersKey;
import com.eason.seckill.seckill.dao.UsersDao;
import com.eason.seckill.seckill.entity.User;
import com.eason.seckill.seckill.exception.GlobalException;
import com.eason.seckill.seckill.result.CodeMsg;
import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.utils.MD5Utils;
import com.eason.seckill.seckill.utils.UUIDUtils;
import com.eason.seckill.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.eason.seckill.seckill.config.redis.keys.UsersKey.TOKEN_EXPIRE;

/**
 * @Author: eason
 * @Date: Created in 16:24 2020/2/28
 * @Description:
 */
@Service
public class UserService {

    public static final String TOKEN_NAME = "token";

    @Autowired
    UsersDao usersDao;

    @Autowired
    RedisService redisService;

    public Result login(HttpServletResponse response, LoginVo loginVo){
        User user = usersDao.getUserByMobile(loginVo.getMobile());
        if(user == null){
            throw new GlobalException(CodeMsg.NOT_FOUND_USER_ERROR.getMsg());
        }
        String inputPwd = loginVo.getPassword();
        String inputToDbPwd = MD5Utils.inputPassEncrypt(inputPwd);
        if(!inputToDbPwd.equals(user.getPassword())){
            return Result.error(CodeMsg.USER_OR_PASSWORD_ERROR);
        }
        //登录成功，生成cookie
        String token = UUIDUtils.getUUID();
        //保存到redis
        redisService.set(UsersKey.TOKEN, token, user);
        Cookie cookie = new Cookie(TOKEN_NAME, token);
        //cookie过期时间
        cookie.setMaxAge(TOKEN_EXPIRE);
        //所有页面生效，都会有cookie信息
        cookie.setPath("/");
        //将cookie返回页面
        response.addCookie(cookie);
        return Result.success(CodeMsg.LOGIN_SUCCESS);
    }
}
