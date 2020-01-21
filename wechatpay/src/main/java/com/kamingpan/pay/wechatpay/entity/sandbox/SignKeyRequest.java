package com.kamingpan.pay.wechatpay.entity.sandbox;

import com.kamingpan.pay.wechatpay.entity.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 获取沙箱版签名密钥请求参数
 *
 * @author kamingpan
 * @since 2018-05-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SignKeyRequest extends BaseRequest {

    public SignKeyRequest() {
        super();
        super.setAppId(null);
        super.setSignType(null);
    }
}
