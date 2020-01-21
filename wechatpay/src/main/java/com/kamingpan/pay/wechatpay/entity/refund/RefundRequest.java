package com.kamingpan.pay.wechatpay.entity.refund;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.entity.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付申请退款请求参数
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundRequest extends BaseRequest {

    // 微信订单号，必须，与商户订单号二选一（微信的订单号，优先使用）
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户订单号，必须，与微信订单号二选一（商户系统内部的订单号，当没提供transaction_id时需要传这个。）
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 商户退款单号，必须（商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔）
    @XmlElement(name = "out_refund_no")
    private String outRefundNo;

    // 订单金额，必须（订单总金额，单位为分，只能为整数）
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 退款金额，必须（退款总金额，订单总金额，单位为分，只能为整数）
    @XmlElement(name = "refund_fee")
    private Long refundFee;

    // 货币种类，非必须（货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "refund_fee_type")
    private String refundFeeType;

    // 退款原因，非必须（若商户传入，会在下发给用户的退款消息中体现退款原因）
    @XmlElement(name = "refund_desc")
    private String refundDesc;

    // 退款资金来源，非必须（仅针对老资金流商户使用，REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款），REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款）
    @XmlElement(name = "refund_account")
    private String refundAccount;

    // 退款结果通知url，非必须（异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数；如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。）
    @XmlElement(name = "notify_url")
    private String notifyUrl = WeChatPayConfig.getWeChatPayRefundNotifyUrl();

}
