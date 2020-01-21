package com.kamingpan.pay.wechatpay.service;

import com.kamingpan.pay.wechatpay.entity.order.JsPaySignature;
import com.kamingpan.pay.wechatpay.enumeration.TradeTypeEnum;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.service.js.WeChatJsPayService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信刷卡支付业务单元测试
 *
 * @author kamingpan
 * @since 2018-06-01
 */
@Slf4j
public class WeChatJsPayServiceTest {

    // 微信支付基础业务
    private WeChatJsPayService weChatJsPayService = new WeChatJsPayService();

    // 金额（单位：分）
    private int amount = 100;
    // 商品描述
    private String body = "body";
    // openid
    private String openid = "openid";
    // 订单号
    private String tradeNo = "tradeNo";
    // 交易类型
    private TradeTypeEnum tradeType = TradeTypeEnum.APP;

    /**
     * 统一下单单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void unifiedOrderTest() throws WeChatPayException {
        JsPaySignature jsPaySignature = weChatJsPayService.jsPayUnifiedOrder(tradeNo, amount, tradeType, body, openid);
        System.out.println(jsPaySignature);
    }

}
