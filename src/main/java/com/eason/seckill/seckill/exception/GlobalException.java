package com.eason.seckill.seckill.exception;

import com.eason.seckill.seckill.result.CodeMsg;

/**
 * @Author: eason
 * @Date: Created in 20:10 2020/2/27
 * @Description:
 */
public class GlobalException extends RuntimeException {
    private CodeMsg codeMsg;

    public GlobalException(String message) {
        super(message);
    }
    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }


    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
