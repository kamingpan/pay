package com.kamingpan.pay.wechatpay.entity.base;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 微信支付基础响应结果
 *
 * @author kamingpan
 * @since 2016-09-08
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseResponse {

    // 返回状态码（SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断）
    @XmlElement(name = "return_code")
    private String returnCode;

    // 返回信息（返回信息，如非空，为错误原因）
    @XmlElement(name = "return_msg")
    private String returnMsg;

    // 业务结果（SUCCESS/FAIL）
    @XmlElement(name = "result_code")
    private String resultCode;

    // 错误代码
    @XmlElement(name = "err_code")
    private String errCode;

    // 错误代码描述（错误返回的信息描述）
    @XmlElement(name = "err_code_des")
    private String errCodeDes;

    // 公众账号ID || 应用ID（微信分配的公众账号ID（企业号corpid即为此appId） || 微信开放平台审核通过的应用APPID）
    @XmlElement(name = "appid")
    private String appId;

    // 商户号（微信支付分配的商户号）
    @XmlElement(name = "mch_id")
    private String mchId;

    // 特约（子）商户应用ID（微信开放平台审核通过的应用APPID，需配合服务商户号一起使用）
    @XmlElement(name = "sub_appid")
    private String subAppId;

    // 特约（子）商户号（微信支付分配的商户号，需配合服务商户号一起使用）
    @XmlElement(name = "sub_mch_id")
    private String subMchId;

    // 设备号（微信支付分配，调用接口提交的终端设备号，与下单一致）
    @XmlElement(name = "device_info")
    private String deviceInfo;

    // 随机字符串（微信返回的随机字符串）
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    // 签名（微信返回的签名)
    @XmlElement(name = "sign")
    private String sign;

}
