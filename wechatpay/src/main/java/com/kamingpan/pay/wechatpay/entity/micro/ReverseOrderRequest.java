package com.kamingpan.pay.wechatpay.entity.micro;

import com.kamingpan.pay.wechatpay.entity.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信刷卡支付撤销订单请求参数
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReverseOrderRequest extends BaseRequest {

    // 微信订单号，与商户订单号二选一（微信的订单号，优先使用）
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户订单号，与微信订单号二选一（商户系统内部的订单号,transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no）
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

}
