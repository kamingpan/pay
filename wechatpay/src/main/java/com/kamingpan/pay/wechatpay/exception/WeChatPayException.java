package com.kamingpan.pay.wechatpay.exception;

/**
 * 微信支付异常
 *
 * @author kamingpan
 * @since 2016-07-18
 */
public class WeChatPayException extends Exception {

    public WeChatPayException() {
    }

    public WeChatPayException(String message) {
        super(message);
    }

    public WeChatPayException(Throwable cause) {
        super(cause);
    }

    public WeChatPayException(String message, Throwable cause) {
        super(message, cause);
    }
}
