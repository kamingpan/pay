package com.kamingpan.pay.wechatpay.entity.sandbox;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 获取沙箱版签名密钥响应结果
 *
 * @author kamingpan
 * @since 2018-05-30
 */
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SignKeyResponse {

    // 返回状态码（SUCCESS/FAIL，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断）
    @XmlElement(name = "return_code")
    private String returnCode;

    // 返回信息（返回信息，如非空，为错误原因）
    @XmlElement(name = "retmsg")
    private String returnMsg;

    // 业务结果（SUCCESS/FAIL）
    @XmlElement(name = "retcode")
    private String resultCode;

    // 返回的沙箱密钥
    @XmlElement(name = "sandbox_signkey")
    private String sandboxSignKey;

}
