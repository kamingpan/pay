package com.kamingpan.pay.wechatpay.service.base;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.constant.WeChatPayConstant;
import com.kamingpan.pay.wechatpay.entity.notify.PayNotifyResult;
import com.kamingpan.pay.wechatpay.entity.notify.RefundNotifyReqInfo;
import com.kamingpan.pay.wechatpay.entity.notify.RefundNotifyResult;
import com.kamingpan.pay.wechatpay.entity.order.CloseOrderRequest;
import com.kamingpan.pay.wechatpay.entity.order.CloseOrderResponse;
import com.kamingpan.pay.wechatpay.entity.order.OrderQueryRequest;
import com.kamingpan.pay.wechatpay.entity.order.OrderQueryResponse;
import com.kamingpan.pay.wechatpay.entity.order.UnifiedOrderRequest;
import com.kamingpan.pay.wechatpay.entity.order.UnifiedOrderResponse;
import com.kamingpan.pay.wechatpay.entity.refund.RefundQueryRequest;
import com.kamingpan.pay.wechatpay.entity.refund.RefundQueryResponse;
import com.kamingpan.pay.wechatpay.entity.refund.RefundRequest;
import com.kamingpan.pay.wechatpay.entity.refund.RefundResponse;
import com.kamingpan.pay.wechatpay.enumeration.TradeTypeEnum;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.util.AES256ECB;
import com.kamingpan.pay.wechatpay.util.HttpsRequest;
import com.kamingpan.pay.wechatpay.util.SSLRequest;
import com.kamingpan.pay.wechatpay.util.Signature;
import com.kamingpan.pay.wechatpay.util.XMLConversion;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 微信支付基础service
 *
 * @author kamingpan
 * @since 2016-12-03
 */
@Slf4j
public class WeChatPayBaseService {

    /**
     * 微信支付统一下单.
     *
     * @param tradeNo   交易号
     * @param amount    交易金额，单位：分
     * @param tradeType 交易类型，参数见支付类型枚举 {@link TradeTypeEnum}
     * @param body      商品或支付单简要描述，该字段须严格按照规范传递
     * @param openid    用户标识，公众号支付必传
     * @return 统一下单响应结果 {@link UnifiedOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public UnifiedOrderResponse unifiedOrder(String tradeNo, long amount, TradeTypeEnum tradeType, String body,
                                             String openid) throws WeChatPayException {
        if (null == tradeNo || tradeNo.trim().isEmpty()) {
            throw new WeChatPayException("微信支付统一下单异常：微信订单号（tradeNo）必须提交");
        }
        if (0 >= amount) {
            throw new WeChatPayException("微信支付统一下单异常：交易金额（amount）必须提交且大于0");
        }
        if (null == tradeType || tradeType.getTradeType().isEmpty()) {
            throw new WeChatPayException("微信支付统一下单异常：交易类型（tradeType）必须提交");
        }
        if (null == body || body.trim().isEmpty()) {
            throw new WeChatPayException("微信支付统一下单异常：商品描述（body）必须提交");
        }

        // 组装请求对象
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.setOutTradeNo(tradeNo);
        unifiedOrderRequest.setTotalFee(amount);
        unifiedOrderRequest.setTradeType(tradeType.getTradeType());
        unifiedOrderRequest.setBody(body);

        // 如果是公众号支付类型，则需要传入openid或sub_openid
        if (TradeTypeEnum.JS_API.equals(tradeType)) {
            if (WeChatPayConfig.isSubMerchant()) {
                unifiedOrderRequest.setSubOpenid(openid);
            } else {
                unifiedOrderRequest.setOpenid(openid);
            }
        }

        return this.unifiedOrder(unifiedOrderRequest, WeChatPayConfig.getKey());
    }

    /**
     * 微信支付统一下单.
     *
     * @param unifiedOrderRequest 微信支付统一下单请求参数
     * @param key                 签名密钥
     * @return 统一下单响应结果 {@link UnifiedOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest unifiedOrderRequest, String key)
            throws WeChatPayException {
        if (null == unifiedOrderRequest) {
            throw new WeChatPayException("微信支付统一下单对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }

        // 签名
        String sign;

        // 定义统一下单响应结果对象
        UnifiedOrderResponse unifiedOrderResponse;

        try {
            // 计算请求签名
            sign = Signature.getSign(unifiedOrderRequest, key, unifiedOrderRequest.getSignType());
            unifiedOrderRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(unifiedOrderRequest);
            log.debug("微信支付统一下单请求地址：{}", WeChatPayConfig.getUnifiedOrderUrl());
            log.debug("微信支付统一下单请求参数：\r\n{}", xml);
            String result = HttpsRequest.sendPost(WeChatPayConfig.getUnifiedOrderUrl(), xml);
            log.debug("微信支付统一下单响应结果：\r\n{}", result);

            // 计算响应签名
            sign = Signature.getSign(result, key, unifiedOrderRequest.getSignType());

            // 微信支付统一下单响应结果转换为对象
            unifiedOrderResponse = XMLConversion.convertToObject(result, UnifiedOrderResponse.class);
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(unifiedOrderResponse.getReturnCode())) {
            throw new WeChatPayException("微信支付统一下单请求失败：" + unifiedOrderResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(unifiedOrderResponse.getSign())) {
            throw new WeChatPayException("微信支付统一下单验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(unifiedOrderResponse.getResultCode())) {
            throw new WeChatPayException("微信支付统一下单业务异常：" + unifiedOrderResponse.getErrCodeDes());
        }
        return unifiedOrderResponse;
    }

    /**
     * 微信支付查询订单（交易号和微信订单号可二选一或者都传）.
     *
     * @param tradeNo       交易号
     * @param transactionId 微信订单号
     * @return 订单查询响应结果 {@link OrderQueryResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public OrderQueryResponse orderQuery(String tradeNo, String transactionId) throws WeChatPayException {
        if ((null == tradeNo || tradeNo.trim().isEmpty())
                && (null == transactionId || transactionId.trim().isEmpty())) {
            throw new WeChatPayException("微信支付查询订单异常" +
                    "微信订单号（tradeNo）和商户订单号（transactionId）必须至少提交一个");
        }

        // 组装请求对象
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.setTransactionId(transactionId);
        orderQueryRequest.setOutTradeNo(tradeNo);

        return this.orderQuery(orderQueryRequest, WeChatPayConfig.getKey());
    }

    /**
     * 微信支付查询订单.
     *
     * @param orderQueryRequest 微信支付查询订单请求参数
     * @param key               签名密钥
     * @return 统一下单响应结果 {@link OrderQueryResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public OrderQueryResponse orderQuery(OrderQueryRequest orderQueryRequest, String key) throws WeChatPayException {
        if (null == orderQueryRequest) {
            throw new WeChatPayException("微信支付查询订单对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }

        // 签名
        String sign;

        // 定义查询订单响应结果对象
        OrderQueryResponse orderQueryResponse;

        try {
            // 计算请求签名
            sign = Signature.getSign(orderQueryRequest, key, orderQueryRequest.getSignType());
            orderQueryRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(orderQueryRequest);
            log.debug("微信支付查询订单请求地址：{}", WeChatPayConfig.getOrderQueryUrl());
            log.debug("微信支付查询订单请求参数：\r\n{}", xml);
            String result = HttpsRequest.sendPost(WeChatPayConfig.getOrderQueryUrl(), xml);
            log.debug("微信支付查询订单响应结果：\r\n{}", result);

            // 计算响应签名
            sign = Signature.getSign(result, key, orderQueryRequest.getSignType());

            // 微信支付查询订单响应结果转换为对象
            orderQueryResponse = XMLConversion.convertToObject(result, OrderQueryResponse.class);
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(orderQueryResponse.getReturnCode())) {
            throw new WeChatPayException("微信支付查询订单请求失败：" + orderQueryResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(orderQueryResponse.getSign())) {
            throw new WeChatPayException("微信支付查询订单验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(orderQueryResponse.getResultCode())) {
            throw new WeChatPayException("微信支付查询订单业务异常：" + orderQueryResponse.getErrCodeDes());
        }
        return orderQueryResponse;
    }

    /**
     * 微信支付关闭订单.
     *
     * @param tradeNo 订单号
     * @return 关闭订单响应结果 {@link CloseOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public CloseOrderResponse closeOrder(String tradeNo) throws WeChatPayException {
        if (null == tradeNo || tradeNo.trim().isEmpty()) {
            throw new WeChatPayException("微信支付关闭订单异常：订单号（tradeNo）必须提交");
        }

        // 组装请求对象
        CloseOrderRequest closeOrderRequest = new CloseOrderRequest();
        closeOrderRequest.setOutTradeNo(tradeNo);

        return this.closeOrder(closeOrderRequest, WeChatPayConfig.getKey());
    }

    /**
     * 微信支付关闭订单.
     *
     * @param closeOrderRequest 微信支付关闭订单请求参数
     * @param key               签名密钥
     * @return 统一下单响应结果 {@link CloseOrderResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public CloseOrderResponse closeOrder(CloseOrderRequest closeOrderRequest, String key) throws WeChatPayException {
        if (null == closeOrderRequest) {
            throw new WeChatPayException("微信支付关闭订单对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }

        // 签名
        String sign;

        // 定义关闭订单响应结果对象
        CloseOrderResponse closeOrderResponse;

        try {
            // 计算请求签名
            sign = Signature.getSign(closeOrderRequest, key, closeOrderRequest.getSignType());
            closeOrderRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(closeOrderRequest);
            log.debug("微信支付关闭订单请求地址：{}", WeChatPayConfig.getCloseOrderUrl());
            log.debug("微信支付关闭订单请求参数：\r\n{}", xml);
            String result = HttpsRequest.sendPost(WeChatPayConfig.getCloseOrderUrl(), xml);
            log.debug("微信支付查询订单响应结果：\r\n{}", result);

            // 计算响应签名
            sign = Signature.getSign(result, key, closeOrderRequest.getSignType());

            // 微信支付关闭订单响应结果转换为对象
            closeOrderResponse = XMLConversion.convertToObject(result, CloseOrderResponse.class);
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(closeOrderResponse.getReturnCode())) {
            throw new WeChatPayException("微信支付关闭订单请求失败：" + closeOrderResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(closeOrderResponse.getSign())) {
            throw new WeChatPayException("微信支付关闭订单验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(closeOrderResponse.getResultCode())) {
            throw new WeChatPayException("微信支付关闭订单业务异常：" + closeOrderResponse.getErrCodeDes());
        }
        return closeOrderResponse;
    }

    /**
     * 微信支付申请退款（微信订单号和商户订单号可二选一）.
     *
     * @param transactionId 微信订单号
     * @param tradeNo       商户订单号
     * @param refundNo      退款单号
     * @param amount        退款金额，单位：分
     * @return 退款申请响应结果 {@link RefundResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public RefundResponse refund(String transactionId, String tradeNo, String refundNo, long amount)
            throws WeChatPayException {
        if ((null == tradeNo || tradeNo.trim().isEmpty())
                && (null == transactionId || transactionId.trim().isEmpty())) {
            throw new WeChatPayException("微信支付申请退款异常：" +
                    "微信订单号（tradeNo）和商户订单号（transactionId）必须至少提交一个");
        }
        if (null == refundNo || refundNo.trim().isEmpty()) {
            throw new WeChatPayException("微信支付申请退款异常：退款单号（refundNo）必须提交");
        }
        if (0 >= amount) {
            throw new WeChatPayException("微信支付申请退款异常：退款金额（amount）必须提交且大于0");
        }

        // 组装请求对象
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setTransactionId(transactionId);
        refundRequest.setOutTradeNo(tradeNo);
        refundRequest.setOutRefundNo(refundNo);
        refundRequest.setTotalFee(amount);
        refundRequest.setRefundFee(amount);

        return this.refund(refundRequest, WeChatPayConfig.getKey(), WeChatPayConfig.getWeChatPayCertificate());
    }

    /**
     * 微信支付申请退款.
     *
     * @param refundRequest 微信支付申请退款请求参数
     * @param key           签名密钥
     * @param certificate   证书路径
     * @return 统一下单响应结果 {@link RefundResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public RefundResponse refund(RefundRequest refundRequest, String key, String certificate)
            throws WeChatPayException {
        return this.refund(refundRequest, key, SSLRequest.getCertificateInputStream(certificate));
    }

    /**
     * 微信支付申请退款.
     *
     * @param refundRequest 微信支付申请退款请求参数
     * @param key           签名密钥
     * @param certificate   证书文件
     * @return 统一下单响应结果 {@link RefundResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public RefundResponse refund(RefundRequest refundRequest, String key, InputStream certificate)
            throws WeChatPayException {
        if (null == refundRequest) {
            throw new WeChatPayException("微信支付申请退款对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }
        if (null == certificate) {
            throw new WeChatPayException("微信支付证书文件不能为空");
        }

        // 签名
        String sign;

        // 定义申请退款响应结果对象
        RefundResponse refundResponse;
        Map<String, String> refundResponseMap;

        try {
            // 计算请求签名
            sign = Signature.getSign(refundRequest, key, refundRequest.getSignType());
            refundRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(refundRequest);
            log.debug("微信支付申请退款请求地址：{}", WeChatPayConfig.getRefundUrl());
            log.debug("微信支付申请退款请求参数：\r\n{}", xml);
            String result = SSLRequest.sendRequest(WeChatPayConfig.getRefundUrl(),
                    xml, certificate, refundRequest.getMchId());
            log.debug("微信支付申请退款响应结果：\r\n{}", result);

            // 微信支付申请退款响应结果转换为对象
            refundResponse = XMLConversion.convertToObject(result, RefundResponse.class);
            refundResponseMap = XMLConversion.convertToMap(result);

            // 计算响应签名
            sign = Signature.getSign(refundResponseMap, key, refundRequest.getSignType());
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(refundResponse.getReturnCode())) {
            throw new WeChatPayException("微信支付申请退款请求失败：" + refundResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(refundResponse.getSign())) {
            throw new WeChatPayException("微信支付申请退款验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(refundResponse.getResultCode())) {
            throw new WeChatPayException("微信支付申请退款业务异常：" + refundResponse.getErrCodeDes());
        }

        // 获取退款代金券使用数量，如果没有使用过，则直接返回结果
        Long couponRefundCount = refundResponse.getCouponRefundCount();
        if (null == couponRefundCount || couponRefundCount <= 0) {
            return refundResponse;
        }

        // 遍历带下标的退款详情，赋值到退款查询响应结果对象中
        for (long i = 0; i < couponRefundCount; i++) {
            // 如果存在，则查询退款详情的信息，赋值到退款查询响应结果对象中
            RefundResponse.Coupon coupon = new RefundResponse.Coupon();
            coupon.setCouponType(refundResponseMap.get("coupon_type_" + i));
            coupon.setCouponRefundId(refundResponseMap.get("coupon_refund_id_" + i));

            String couponRefundFee = refundResponseMap.get("coupon_refund_fee_" + i);
            coupon.setCouponRefundFee(null == couponRefundFee || couponRefundFee.isEmpty()
                    ? null : Long.valueOf(couponRefundFee));
            refundResponse.addCoupon(coupon);
        }

        return refundResponse;
    }

    /**
     * 微信支付退款查询（微信订单号、商户订单号、商户退款单号、微信退款单号参数可四选一）.
     *
     * @param transactionId 微信订单号
     * @param tradeNo       商户订单号
     * @param refundNo      商户退款单号
     * @param refundId      微信退款单号
     * @return 退款查询响应结果 {@link RefundQueryResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public RefundQueryResponse refundQuery(String transactionId, String tradeNo, String refundNo, String refundId)
            throws WeChatPayException {
        if ((null == tradeNo || tradeNo.trim().isEmpty())
                && (null == transactionId || transactionId.trim().isEmpty())
                && (null == refundNo || refundNo.trim().isEmpty())
                && (null == refundId || refundId.trim().isEmpty())) {
            throw new WeChatPayException("微信支付申请退款异常：微信订单号（tradeNo）、商户订单号（transactionId）、" +
                    "商户退款单号（refundNo）、微信退款单号（refundId）必须至少提交一个");
        }

        // 组装请求对象
        RefundQueryRequest refundQueryRequest = new RefundQueryRequest();
        refundQueryRequest.setTransactionId(transactionId);
        refundQueryRequest.setOutTradeNo(tradeNo);
        refundQueryRequest.setOutRefundNo(refundNo);
        refundQueryRequest.setRefundId(refundId);

        return this.refundQuery(refundQueryRequest, WeChatPayConfig.getKey());
    }

    /**
     * 微信支付退款查询
     *
     * @param refundQueryRequest 微信支付退款查询请求参数
     * @param key                签名密钥
     * @return 统一下单响应结果 {@link RefundQueryResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public RefundQueryResponse refundQuery(RefundQueryRequest refundQueryRequest, String key)
            throws WeChatPayException {
        if (null == refundQueryRequest) {
            throw new WeChatPayException("微信支付退款查询对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }

        // 签名
        String sign;

        // 定义退款查询响应结果对象
        RefundQueryResponse refundQueryResponse;
        Map<String, String> refundQueryResponseMap;

        try {
            // 计算请求签名
            sign = Signature.getSign(refundQueryRequest, key, refundQueryRequest.getSignType());
            refundQueryRequest.setSign(sign);

            // 处理响应结果
            String xml = XMLConversion.convertToXML(refundQueryRequest);
            log.debug("微信支付退款查询请求地址：{}", WeChatPayConfig.getRefundQueryUrl());
            log.debug("微信支付退款查询请求参数：\r\n{}", xml);
            String result = HttpsRequest.sendPost(WeChatPayConfig.getRefundQueryUrl(), xml);
            log.debug("微信支付退款查询响应结果：\r\n{}", result);

            // 微信支付退款查询响应结果转换为对象
            refundQueryResponse = XMLConversion.convertToObject(result, RefundQueryResponse.class);
            refundQueryResponseMap = XMLConversion.convertToMap(result);

            // 计算响应签名
            sign = Signature.getSign(refundQueryResponseMap, key, refundQueryRequest.getSignType());
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(refundQueryResponse.getReturnCode())) {
            throw new WeChatPayException("微信支付退款查询请求失败：" + refundQueryResponse.getReturnMsg());
        }

        // 验证签名
        if (null == sign || !sign.equals(refundQueryResponse.getSign())) {
            throw new WeChatPayException("微信支付退款查询验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(refundQueryResponse.getResultCode())) {
            throw new WeChatPayException("微信支付退款查询业务异常：" + refundQueryResponse.getErrCodeDes());
        }

        // 遍历带下标的退款详情，赋值到退款查询响应结果对象中
        for (int i = 0; true; i++) {
            // 查询退款单号是否存在，如果不存在，则直接结束循环
            String outRefundNo = refundQueryResponseMap.get("out_refund_no_" + i);
            if (null == outRefundNo || outRefundNo.isEmpty()) {
                break;
            }

            // 如果存在，则查询退款详情的信息，赋值到退款查询响应结果对象中
            RefundQueryResponse.Refund refund = new RefundQueryResponse.Refund();
            refund.setOutRefundNo(refundQueryResponseMap.get("out_refund_no_" + i));
            refund.setRefundId(refundQueryResponseMap.get("refund_id_" + i));
            refund.setRefundChannel(refundQueryResponseMap.get("refund_channel_" + i));
            refund.setRefundStatus(refundQueryResponseMap.get("refund_status_" + i));
            refund.setRefundAccount(refundQueryResponseMap.get("refund_account_" + i));
            refund.setRefundReceiveAccount(refundQueryResponseMap.get("refund_recv_accout_" + i));
            refund.setRefundSuccessTime(refundQueryResponseMap.get("refund_success_time_" + i));

            String refundFee = refundQueryResponseMap.get("refund_fee_" + i);
            refund.setRefundFee(null == refundFee || refundFee.isEmpty() ? null : Long.valueOf(refundFee));
            String settlementRefundFee = refundQueryResponseMap.get("settlement_refund_fee_" + i);
            refund.setSettlementRefundFee(null == settlementRefundFee || settlementRefundFee.isEmpty()
                    ? null : Long.valueOf(settlementRefundFee));
            refundQueryResponse.addRefund(refund);
        }

        return refundQueryResponse;
    }

    /**
     * 微信支付回调处理.
     *
     * @param xml 回调结果（xml格式）
     * @param key 签名密钥
     * @return 回调结果对象 {@link PayNotifyResult}
     * @throws WeChatPayException 微信支付异常
     */
    public PayNotifyResult payNotify(String xml, String key) throws WeChatPayException {
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }
        if (null == xml || xml.isEmpty()) {
            throw new WeChatPayException("微信支付回调通知结果为空");
        }

        log.debug("微信支付回调通知结果：\r\n{}", xml);

        // 微信支付回调结果转换为对象
        PayNotifyResult payNotifyResult;
        try {
            payNotifyResult = XMLConversion.convertToObject(xml, PayNotifyResult.class);
        } catch (JAXBException | UnsupportedEncodingException exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(payNotifyResult.getReturnCode())) {
            throw new WeChatPayException("微信支付回调结果通知异常：" + payNotifyResult.getReturnMsg());
        }

        // 计算响应签名
        String sign;
        try {
            sign = Signature.getSign(xml, key, WeChatPayConfig.getSignType());
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        // 验证签名
        if (null == sign || !sign.equals(payNotifyResult.getSign())) {
            throw new WeChatPayException("微信支付回调通知验证签名失败");
        }

        if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(payNotifyResult.getResultCode())) {
            throw new WeChatPayException("微信支付回调通知业务异常：" + payNotifyResult.getErrCodeDes());
        }
        return payNotifyResult;
    }

    /**
     * 微信支付退款回调处理.
     *
     * @param xml 回调结果（xml格式）
     * @param key 签名密钥
     * @return 回调结果对象 {@link RefundNotifyResult}
     * @throws WeChatPayException 微信支付异常
     */
    public RefundNotifyResult refundNotify(String xml, String key) throws WeChatPayException {
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("签名密钥不能为空");
        }

        log.debug("微信支付退款回调通知结果：\r\n{}", xml);

        // 微信支付回调结果转换为对象
        RefundNotifyResult refundNotifyResult;
        try {
            refundNotifyResult = XMLConversion.convertToObject(xml, RefundNotifyResult.class);
        } catch (JAXBException | UnsupportedEncodingException exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(refundNotifyResult.getReturnCode())) {
            throw new WeChatPayException("微信支付退款回调结果通知异常：" + refundNotifyResult.getReturnMsg());
        }

        // 获得加密串
        String reqInfo = refundNotifyResult.getReqInfo();

        // AES256ECB解密
        try {
            reqInfo = AES256ECB.decrypt(reqInfo, key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | UnsupportedEncodingException exception) {
            throw new WeChatPayException(exception);
        }

        // 加密串转换为对象
        RefundNotifyReqInfo refundNotifyReqInfo;
        try {
            refundNotifyReqInfo = XMLConversion.convertToObject(reqInfo, RefundNotifyReqInfo.class);
        } catch (JAXBException | UnsupportedEncodingException exception) {
            throw new WeChatPayException(exception);
        }
        refundNotifyResult.setRefundNotifyReqInfo(refundNotifyReqInfo);

        return refundNotifyResult;
    }

}
