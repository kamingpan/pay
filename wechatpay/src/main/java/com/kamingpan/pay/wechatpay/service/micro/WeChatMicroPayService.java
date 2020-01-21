package com.kamingpan.pay.wechatpay.service.micro;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.constant.WeChatPayConstant;
import com.kamingpan.pay.wechatpay.entity.micro.MicroPayRequest;
import com.kamingpan.pay.wechatpay.entity.micro.MicroPayResponse;
import com.kamingpan.pay.wechatpay.entity.micro.ReverseOrderRequest;
import com.kamingpan.pay.wechatpay.entity.micro.ReverseOrderResponse;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.service.base.WeChatPayBaseService;
import com.kamingpan.pay.wechatpay.util.HttpsRequest;
import com.kamingpan.pay.wechatpay.util.SSLRequest;
import com.kamingpan.pay.wechatpay.util.Signature;
import com.kamingpan.pay.wechatpay.util.XMLConversion;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 微信刷卡支付service
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Slf4j
public class WeChatMicroPayService extends WeChatPayBaseService {

    /**
     * 微信刷卡支付.
     *
     * @param tradeNo  交易号
     * @param amount   交易金额，单位：分
     * @param body     商品或支付单简要描述，该字段须严格按照规范传递
     * @param authCode 授权码
     * @return 刷卡支付响应结果 {@link MicroPayResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public MicroPayResponse microPay(String tradeNo, long amount, String body, String authCode)
            throws WeChatPayException {
        if (null == tradeNo || tradeNo.trim().isEmpty()) {
            throw new WeChatPayException("微信刷卡支付异常：交易号（tradeNo）必须提交");
        }
        if (0 >= amount) {
            throw new WeChatPayException("微信刷卡支付异常：交易金额（amount）必须提交且大于0");
        }
        if (null == authCode || authCode.trim().isEmpty()) {
            throw new WeChatPayException("微信刷卡支付异常：授权码（authCode）必须提交");
        }
        if (null == body || body.trim().isEmpty()) {
            throw new WeChatPayException("微信刷卡支付异常：商品描述（body）必须提交");
        }

        // 组装请求对象
        MicroPayRequest microPayRequest = new MicroPayRequest();
        microPayRequest.setOutTradeNo(tradeNo);
        microPayRequest.setTotalFee(amount);
        microPayRequest.setBody(body);
        microPayRequest.setAuthCode(authCode);

        return this.microPay(microPayRequest, WeChatPayConfig.getKey());
    }

    /**
     * 微信刷卡支付.
     *
     * @param microPayRequest 微信刷卡支付请求参数
     * @param key             签名密钥
     * @return 统一下单响应结果 {@link MicroPayResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public MicroPayResponse microPay(MicroPayRequest microPayRequest, String key) throws WeChatPayException {
        if (null == microPayRequest) {
            throw new WeChatPayException("微信刷卡支付对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }

        // 签名
        String sign;

        // 定义刷卡支付响应结果对象
        MicroPayResponse microPayResponse;

        try {
            // 计算请求签名
            sign = Signature.getSign(microPayRequest, WeChatPayConfig.getKey(), microPayRequest.getSignType());
            microPayRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(microPayRequest);
            log.debug("微信刷卡支付请求地址：{}", WeChatPayConfig.getMicroPayUrl());
            log.debug("微信刷卡支付请求参数：\r\n{}", xml);
            String result = HttpsRequest.sendPost(WeChatPayConfig.getMicroPayUrl(), xml);
            log.debug("微信刷卡支付响应结果：\r\n{}", result);

            // 计算响应签名
            sign = Signature.getSign(result, key, microPayRequest.getSignType());

            // 微信刷卡支付响应结果转换为对象
            microPayResponse = XMLConversion.convertToObject(result, MicroPayResponse.class);
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(microPayResponse.getReturnCode())) {
            throw new WeChatPayException("微信刷卡支付请求失败：" + microPayResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(microPayResponse.getSign())) {
            throw new WeChatPayException("微信刷卡支付验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(microPayResponse.getResultCode())) {
            throw new WeChatPayException("微信刷卡支付业务异常：" + microPayResponse.getErrCodeDes());
        }
        return microPayResponse;
    }

    /**
     * 微信刷卡支付撤销订单（微信订单号和商户订单号可二选一）.
     *
     * @param tradeNo       商户订单号
     * @param transactionId 微信订单号
     * @return 撤销订单响应结果 {@link ReverseOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public ReverseOrderResponse reverse(String tradeNo, String transactionId) throws WeChatPayException {
        if ((null == tradeNo || tradeNo.trim().isEmpty())
                && (null == transactionId || transactionId.trim().isEmpty())) {
            throw new WeChatPayException("微信刷卡支付撤销订单异常：" +
                    "微信订单号（tradeNo）和商户订单号（transactionId）必须至少提交一个");
        }

        // 组装请求对象
        ReverseOrderRequest reverseOrderRequest = new ReverseOrderRequest();
        reverseOrderRequest.setOutTradeNo(tradeNo);
        reverseOrderRequest.setTransactionId(transactionId);

        return this.reverse(reverseOrderRequest, WeChatPayConfig.getKey(), WeChatPayConfig.getWeChatPayCertificate());
    }

    /**
     * 微信刷卡支付撤销订单（微信订单号和商户订单号可二选一）.
     *
     * @param reverseOrderRequest 微信刷卡支付撤销订单请求参数
     * @param key                 签名密钥
     * @param certificate         证书路径
     * @return 撤销订单响应结果 {@link ReverseOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public ReverseOrderResponse reverse(ReverseOrderRequest reverseOrderRequest, String key, String certificate)
            throws WeChatPayException {
        return this.reverse(reverseOrderRequest, key, SSLRequest.getCertificateInputStream(certificate));
    }

    /**
     * 微信刷卡支付撤销订单（微信订单号和商户订单号可二选一）.
     *
     * @param reverseOrderRequest 微信刷卡支付撤销订单请求参数
     * @param key                 签名密钥
     * @param certificate         证书文件
     * @return 撤销订单响应结果 {@link ReverseOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public ReverseOrderResponse reverse(ReverseOrderRequest reverseOrderRequest, String key, InputStream certificate)
            throws WeChatPayException {
        if (null == reverseOrderRequest) {
            throw new WeChatPayException("微信刷卡支付撤销订单对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }
        if (null == certificate) {
            throw new WeChatPayException("微信支付证书文件不能为空");
        }

        // 签名
        String sign;

        // 定义刷卡支付撤销订单响应结果对象
        ReverseOrderResponse reverseOrderResponse;

        try {
            // 计算请求签名
            sign = Signature.getSign(reverseOrderRequest, WeChatPayConfig.getKey(), reverseOrderRequest.getSignType());
            reverseOrderRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(reverseOrderRequest);
            log.debug("微信刷卡支付撤销订单请求地址：{}", WeChatPayConfig.getReverseOrderUrl());
            log.debug("微信刷卡支付撤销订单请求参数：\r\n{}", xml);
            String result = SSLRequest.sendRequest(WeChatPayConfig.getReverseOrderUrl(), xml, certificate, reverseOrderRequest.getMchId());
            log.debug("微信刷卡支付撤销订单响应结果：\r\n{}", result);

            // 计算响应签名
            sign = Signature.getSign(result, key, reverseOrderRequest.getSignType());

            // 微信刷卡支付撤销订单响应结果转换为对象
            reverseOrderResponse = XMLConversion.convertToObject(result, ReverseOrderResponse.class);
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(reverseOrderResponse.getReturnCode())) {
            throw new WeChatPayException("微信刷卡支付撤销订单请求失败：" + reverseOrderResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(reverseOrderResponse.getSign())) {
            throw new WeChatPayException("微信刷卡支付撤销订单验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(reverseOrderResponse.getResultCode())) {
            throw new WeChatPayException("微信刷卡支付业务异常：" + reverseOrderResponse.getErrCodeDes());
        }
        return reverseOrderResponse;
    }

}
