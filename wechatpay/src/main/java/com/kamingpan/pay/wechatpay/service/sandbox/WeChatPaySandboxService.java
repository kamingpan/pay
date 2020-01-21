package com.kamingpan.pay.wechatpay.service.sandbox;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.constant.WeChatPayConstant;
import com.kamingpan.pay.wechatpay.entity.sandbox.SignKeyRequest;
import com.kamingpan.pay.wechatpay.entity.sandbox.SignKeyResponse;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.util.HttpsRequest;
import com.kamingpan.pay.wechatpay.util.Signature;
import com.kamingpan.pay.wechatpay.util.XMLConversion;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付沙箱service
 *
 * @author kamingpan
 * @since 2018-05-30
 */
@Slf4j
public class WeChatPaySandboxService {

    /**
     * 获取沙箱版签名密钥.
     *
     * @return 获取沙箱版签名密钥响应结果 {@link SignKeyResponse}
     * @throws WeChatPayException 微信支付异常
     */
    public SignKeyResponse getSignKey() throws WeChatPayException {
        // 组装请求对象
        SignKeyRequest signKeyRequest = new SignKeyRequest();

        // 定义获取沙箱版签名密钥响应结果对象
        SignKeyResponse signKeyResponse;

        try {
            // 计算请求签名
            String sign = Signature.getSign(signKeyRequest, WeChatPayConfig.getKey(), WeChatPayConstant.SignType.MD5);
            signKeyRequest.setSign(sign);

            String xml = XMLConversion.convertToXML(signKeyRequest);
            log.debug("微信支付获取沙箱版签名密钥请求地址：{}", WeChatPayConfig.getSandboxSignKeyUrl());
            log.debug("微信支付获取沙箱版签名密钥请求参数：\r\n{}", xml);
            String result = HttpsRequest.sendPost(WeChatPayConfig.getSandboxSignKeyUrl(), xml);
            log.debug("微信支付获取沙箱版签名密钥响应结果：\r\n{}", result);

            // 微信支付获取沙箱版签名密钥响应结果转换为对象
            signKeyResponse = XMLConversion.convertToObject(result, SignKeyResponse.class);
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }

        if (!WeChatPayConstant.ResponseStatus.RETURN_SUCCESS.equals(signKeyResponse.getReturnCode())) {
            throw new WeChatPayException("微信支付获取沙箱版签名密钥请求失败：" + signKeyResponse.getReturnMsg());
        }

        /*if (!WeChatPayConstant.ResponseStatus.RESULT_SUCCESS.equals(signKeyResponse.getResultCode())) {
            throw new WeChatPayException("微信支付获取沙箱版签名密钥业务异常：" + signKeyResponse.getErrCodeDes());
        }*/
        return signKeyResponse;
    }

}
