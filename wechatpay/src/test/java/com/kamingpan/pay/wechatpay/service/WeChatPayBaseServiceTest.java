package com.kamingpan.pay.wechatpay.service;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.entity.notify.PayNotifyResult;
import com.kamingpan.pay.wechatpay.entity.notify.RefundNotifyResult;
import com.kamingpan.pay.wechatpay.entity.order.CloseOrderResponse;
import com.kamingpan.pay.wechatpay.entity.order.OrderQueryResponse;
import com.kamingpan.pay.wechatpay.entity.order.UnifiedOrderResponse;
import com.kamingpan.pay.wechatpay.entity.refund.RefundQueryResponse;
import com.kamingpan.pay.wechatpay.entity.refund.RefundResponse;
import com.kamingpan.pay.wechatpay.enumeration.TradeTypeEnum;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.service.base.WeChatPayBaseService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付基础业务单元测试
 *
 * @author kamingpan
 * @since 2018-06-01
 */
@Slf4j
public class WeChatPayBaseServiceTest {

    // 微信支付基础业务
    private WeChatPayBaseService weChatPayBaseService = new WeChatPayBaseService();

    // 金额（单位：分）
    private int amount = 100;
    // 商品描述
    private String body = "body";
    // openid
    private String openid = "openid";
    // 订单号
    private String tradeNo = "tradeNo";
    // 退款单号
    private String refundNo = "refundNo";
    // 授权码（用户展示二维码信息）
    private String authCode = "authCode";
    // 交易类型
    private TradeTypeEnum tradeType = TradeTypeEnum.APP;

    /**
     * 统一下单单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void unifiedOrderTest() throws WeChatPayException {
        UnifiedOrderResponse unifiedOrderResponse = weChatPayBaseService.unifiedOrder(tradeNo, amount, tradeType, body, openid);
        System.out.println(unifiedOrderResponse);
    }

    /**
     * 查询订单单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void orderQueryTest() throws WeChatPayException {
        OrderQueryResponse orderQueryResponse = weChatPayBaseService.orderQuery(tradeNo, null);
        System.out.println(orderQueryResponse);
    }

    /**
     * 关闭订单单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void closeOrderTest() throws WeChatPayException {
        CloseOrderResponse closeOrderResponse = weChatPayBaseService.closeOrder(tradeNo);
        System.out.println(closeOrderResponse);
    }

    /**
     * 退款申请单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void refundTest() throws WeChatPayException {
        RefundResponse refundResponse = weChatPayBaseService.refund(null, tradeNo, refundNo, amount);
        System.out.println(refundResponse);
    }

    /**
     * 退款查询单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void refundQueryTest() throws WeChatPayException {
        RefundQueryResponse refundQueryResponse = weChatPayBaseService.refundQuery(null, tradeNo, refundNo, null);
        System.out.println(refundQueryResponse);
    }

    /**
     * 支付回调单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void payNotifyTest() throws WeChatPayException {
        String xml = "<xml></xml>";
        PayNotifyResult payNotifyResult = weChatPayBaseService.payNotify(xml, WeChatPayConfig.getKey());
        System.out.println(payNotifyResult);
    }

    /**
     * 退款回调单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void refundNotifyTest() throws WeChatPayException {
        String xml = "<xml></xml>";
        RefundNotifyResult refundNotifyResult = weChatPayBaseService.refundNotify(xml, WeChatPayConfig.getKey());
        System.out.println(refundNotifyResult);
    }

}
