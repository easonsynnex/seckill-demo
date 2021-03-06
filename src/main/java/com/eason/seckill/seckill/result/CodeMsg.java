package com.eason.seckill.seckill.result;

/**
 * @Author: eason
 * @Date: Created in 20:11 2020/2/27
 * @Description:
 */
public class CodeMsg {
    int code;
    String msg;

    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");

    public static final CodeMsg ARGUMENT_ERROR = new CodeMsg(100100,"参数校验失败");
    public static final CodeMsg NOT_FOUND_USER_ERROR = new CodeMsg(100110,"该用户不存在");
    public static final CodeMsg USER_OR_PASSWORD_ERROR = new CodeMsg(100120,"用户名或密码不正确");
    public static final CodeMsg USER_NOT_LOGIN = new CodeMsg(100130,"用户名未登录");

    public static final CodeMsg LOGIN_SUCCESS = new CodeMsg(600100,"登录成功");
    public static final CodeMsg SECKILL_OVER = new CodeMsg(600110,"秒杀结束");
    public static final CodeMsg SECKILL_SUCCESS = new CodeMsg(600120,"秒杀成功");
    public static final CodeMsg SECKILL_REPEAT = new CodeMsg(600130,"重复秒杀");

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(String str){
        return new CodeMsg(this.code, this.getMsg() +":" + str);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
