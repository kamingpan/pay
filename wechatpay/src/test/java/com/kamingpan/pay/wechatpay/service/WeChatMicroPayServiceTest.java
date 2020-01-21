package com.kamingpan.pay.wechatpay.service;

import com.kamingpan.pay.wechatpay.entity.micro.MicroPayResponse;
import com.kamingpan.pay.wechatpay.entity.micro.ReverseOrderResponse;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.service.micro.WeChatMicroPayService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信刷卡支付业务单元测试
 *
 * @author kamingpan
 * @since 2018-06-01
 */
@Slf4j
public class WeChatMicroPayServiceTest {

    // 微信刷卡支付业务
    private WeChatMicroPayService weChatMicroPayService = new WeChatMicroPayService();

    // 金额（单位：分）
    private int amount = 100;
    // 商品描述
    private String body = "body";
    // 订单号
    private String tradeNo = "tradeNo";
    // 授权码（用户展示二维码信息）
    private String authCode = "authCode";

    /**
     * 刷卡支付单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void microPayTest() throws WeChatPayException {
        MicroPayResponse microPayResponse = weChatMicroPayService.microPay(tradeNo, amount, body, authCode);
        System.out.println(microPayResponse);
    }

    /**
     * 撤销支付单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void reverseTest() throws WeChatPayException {
        ReverseOrderResponse reverseOrderResponse = weChatMicroPayService.reverse(tradeNo, null);
        System.out.println(reverseOrderResponse);
    }

}
