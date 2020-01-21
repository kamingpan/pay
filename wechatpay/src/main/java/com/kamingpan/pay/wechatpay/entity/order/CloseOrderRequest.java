package com.kamingpan.pay.wechatpay.entity.order;

import com.kamingpan.pay.wechatpay.entity.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付关闭订单请求参数
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class CloseOrderRequest extends BaseRequest {

    // 商户订单号，必须（商户系统内部的订单号）
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

}
