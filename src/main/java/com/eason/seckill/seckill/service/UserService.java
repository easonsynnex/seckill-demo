package com.eason.seckill.seckill.service;

import com.eason.seckill.seckill.dao.UsersDao;
import com.eason.seckill.seckill.entity.User;
import com.eason.seckill.seckill.exception.GlobalException;
import com.eason.seckill.seckill.result.CodeMsg;
import com.eason.seckill.seckill.result.Result;
import com.eason.seckill.seckill.utils.MD5Utils;
import com.eason.seckill.seckill.vo.LoginVo;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: eason
 * @Date: Created in 16:24 2020/2/28
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    UsersDao usersDao;

    public Result login(LoginVo loginVo){
        User user = usersDao.getUserByMobile(loginVo.getMobile());
        if(user == null){
            throw new GlobalException(CodeMsg.NOT_FOUND_USER_ERROR.getMsg());
        }
        String inputPwd = loginVo.getPassword();
        String inputToDbPwd = MD5Utils.inputPassEncrypt(inputPwd);
        if(inputToDbPwd.equals(user.getPassword())){
            return Result.success(CodeMsg.LOGIN_SUCCESS);
        }else{
            return Result.error(CodeMsg.USER_OR_PASSWORD_ERROR);
        }
    }
}
