package com.kamingpan.pay.wechatpay.entity.base;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.util.RandomStringGenerator;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 微信支付基础请求参数
 *
 * @author kamingpan
 * @since 2016-09-08
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseRequest {

    // 公众账号ID || 应用ID，必须（微信分配的公众账号ID（企业号corpid即为此appId） || 微信开放平台审核通过的应用APPID）
    @XmlElement(name = "appid")
    private String appId = WeChatPayConfig.getAppId();

    // 商户号，必须（微信支付分配的商户号）
    @XmlElement(name = "mch_id")
    private String mchId = WeChatPayConfig.getMchId();

    // 特约（子）商户应用ID，必须（微信开放平台审核通过的应用APPID，需配合服务商户号一起使用）
    @XmlElement(name = "sub_appid")
    private String subAppId;

    // 特约（子）商户号，必须（微信支付分配的商户号，需配合服务商户号一起使用）
    @XmlElement(name = "sub_mch_id")
    private String subMchId;

    // 设备号，非必须（终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"）
    @XmlElement(name = "device_info")
    private String deviceInfo;

    // 随机字符串，必须（随机字符串，不长于32位）
    @XmlElement(name = "nonce_str")
    private String nonceStr = RandomStringGenerator.getRandomString();

    // 签名，必须
    @XmlElement(name = "sign")
    private String sign;

    // 签名类型，非必须（目前支持HMAC-SHA256和MD5，默认为MD5）
    @XmlElement(name = "sign_type")
    private String signType = WeChatPayConfig.getSignType();

    public BaseRequest() {
        // 判断是否服务商和特约（子）商户号组合使用，若是，则赋值特约（子）商户号
        if (WeChatPayConfig.isSubMerchant()) {
            this.subAppId = WeChatPayConfig.getSubAppId();
            this.subMchId = WeChatPayConfig.getSubMchId();
        }
    }

}
