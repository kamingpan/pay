package com.kamingpan.pay.wechatpay.entity.refund;

import com.kamingpan.pay.wechatpay.entity.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付退款查询请求参数
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundQueryRequest extends BaseRequest {

    // 微信订单号，与商户订单号，商户退款单号，微信退款单号四选一
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户订单号，与微信订单号，商户退款单号，微信退款单号四选一
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 商户退款单号，与微信订单号，商户订单号，微信退款单号四选一
    @XmlElement(name = "out_refund_no")
    private String outRefundNo;

    // 微信退款单号，与微信订单号，商户订单号，商户退款单号四选一
    @XmlElement(name = "refund_id")
    private String refundId;

    // 偏移量，非必须（偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录）
    @XmlElement(name = "offset")
    private Integer offset;

}
