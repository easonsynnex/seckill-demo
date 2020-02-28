package com.eason.seckill.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: eason
 * @Date: Created in 22:20 2020/2/26
 * @Description: 密码加密,
 * 密码比对实现：前端需要md5加密一次，将加密过的字符传给后台服务，后台服务再加密一次后与数据库比较
 * 保存密码实现：用户输入密码后，将加密过的字符传给后台服务，后台服务再加密一次后保存到数据库
 */
public class MD5Utils {
    /**
     * 前后端的salt要一致
     */
    private static final String SALT = "1a2b3c4d";

    /**
     * 将用户输入的密码第一次加密
     * @param inputPass 用户输入的原始密码
     * @return
     */
    public static String inputPassEncrypt(String inputPass){
        String salt = getRealSalt(inputPass);
        return DigestUtils.md5Hex(salt);
    }

    /**
     * 将第一次加密的密码再次加密
     * @param passFirst
     * @return
     */
    public static String inputPassEncryptSeconds(String passFirst){
        return inputPassEncrypt(passFirst);
    }

    /**
     * 固定的salt
     * @param str
     * @return
     */
    private static String getRealSalt(String str){
        return SALT.charAt(0) + SALT.charAt(2) + str + SALT.charAt(4) + SALT.charAt(6);
    }

    public static void main(String[] args) {
        //调用2次是为了后台生成测试密码保存到数据库
        String input = "123456";
        String two = inputPassEncryptSeconds(inputPassEncrypt(input));
        System.out.println(two);
    }
}
