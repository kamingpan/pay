package com.kamingpan.pay.wechatpay.entity.order;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.util.RandomStringGenerator;
import com.kamingpan.pay.wechatpay.util.Signature;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

/**
 * js支付签名
 *
 * @author kamingpan
 * @since 2018-11-06
 */
@Data
public class JsPaySignature {

    // 公众号名称，由商户传入
    private String appId = WeChatPayConfig.getAppId();

    // 时间戳，自1970年以来的秒数
    private String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);

    // 随机串
    private String nonceStr = RandomStringGenerator.getRandomString();

    // 预支付id
    @XmlElement(name = "package")
    private String prepayId;

    // 微信签名方式
    private String signType = WeChatPayConfig.getSignType();

    // 微信签名
    private String paySign;

    /**
     * 设置带前缀的预支付id
     *
     * @param prepayId 预支付id
     */
    public void setPrefixPrepayId(String prepayId) {
        this.prepayId = ("prepay_id=" + prepayId);
    }

    /**
     * 签名
     *
     * @param jsPaySignature js支付签名对象
     * @throws WeChatPayException 微信支付异常
     */
    public static void sign(JsPaySignature jsPaySignature) throws WeChatPayException {
        JsPaySignature.sign(jsPaySignature, WeChatPayConfig.getKey());
    }

    /**
     * 签名
     *
     * @param jsPaySignature js支付签名对象
     * @param key            密钥
     * @throws WeChatPayException 微信支付异常
     */
    public static void sign(JsPaySignature jsPaySignature, String key) throws WeChatPayException {
        if (null == jsPaySignature) {
            throw new WeChatPayException("签名对象不能为空");
        }
        if (null == key || key.isEmpty()) {
            throw new WeChatPayException("微信支付加密密钥不能为空");
        }

        try {
            jsPaySignature.setPaySign(Signature.getSign(jsPaySignature, key, jsPaySignature.getSignType()));
        } catch (Exception exception) {
            throw new WeChatPayException(exception);
        }
    }

}
