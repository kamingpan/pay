package com.kamingpan.pay.wechatpay.service.js;

import com.kamingpan.pay.wechatpay.entity.order.JsPaySignature;
import com.kamingpan.pay.wechatpay.entity.order.UnifiedOrderRequest;
import com.kamingpan.pay.wechatpay.entity.order.UnifiedOrderResponse;
import com.kamingpan.pay.wechatpay.enumeration.TradeTypeEnum;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.service.base.WeChatPayBaseService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信JSAPI支付service
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Slf4j
public class WeChatJsPayService extends WeChatPayBaseService {

    /**
     * 微信支付统一下单（响应js支付签名对象）.
     *
     * @param tradeNo   交易号
     * @param amount    交易金额，单位：分
     * @param tradeType 交易类型，参数见支付类型枚举 {@link TradeTypeEnum}
     * @param body      商品或支付单简要描述，该字段须严格按照规范传递
     * @param openid    用户标识，公众号支付必传
     * @return 统一下单响应结果 {@link UnifiedOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public JsPaySignature jsPayUnifiedOrder(String tradeNo, long amount, TradeTypeEnum tradeType, String body,
                                            String openid) throws WeChatPayException {
        // 统一下单
        UnifiedOrderResponse unifiedOrderResponse = super.unifiedOrder(tradeNo, amount, tradeType, body, openid);

        // 组装js支付签名对象
        JsPaySignature jsPaySignature = new JsPaySignature();
        jsPaySignature.setPrepayId(unifiedOrderResponse.getPrepayId());
        JsPaySignature.sign(jsPaySignature);
        return jsPaySignature;
    }

    /**
     * 微信支付统一下单（响应js支付签名对象）.
     *
     * @param unifiedOrderRequest 微信支付统一下单请求参数
     * @param key                 签名密钥
     * @return 统一下单响应结果 {@link UnifiedOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public JsPaySignature jsPayUnifiedOrder(UnifiedOrderRequest unifiedOrderRequest, String key)
            throws WeChatPayException {
        // 统一下单
        UnifiedOrderResponse unifiedOrderResponse = super.unifiedOrder(unifiedOrderRequest, key);

        // 组装js支付签名对象
        JsPaySignature jsPaySignature = new JsPaySignature();
        jsPaySignature.setAppId(unifiedOrderResponse.getAppId());
        jsPaySignature.setPrepayId(unifiedOrderResponse.getPrepayId());
        JsPaySignature.sign(jsPaySignature);
        return jsPaySignature;
    }

}
