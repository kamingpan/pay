package com.kamingpan.pay.wechatpay.entity.notify;

import com.kamingpan.pay.wechatpay.entity.base.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 微信支付退款回调
 *
 * @author kamingpan
 * @since 2018-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundNotifyResult extends BaseResponse {

    // 加密信息（加密信息请用商户秘钥进行解密）
    @XmlElement(name = "req_info")
    private String reqInfo;

    // 回调信息“reqInfo”解密后实体
    @XmlTransient
    private RefundNotifyReqInfo refundNotifyReqInfo;

}
