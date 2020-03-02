package com.eason.seckill.seckill.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: eason
 * @Date: Created in 22:10 2020/2/27
 * @Description:
 */
public class LoginVo {

    @Pattern(regexp = "1\\d{10}")
    private String mobile;

    @NotNull
    @Length(min = 6)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
