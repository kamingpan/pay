package com.kamingpan.pay.wechatpay.entity.notify;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付退款回调内容
 *
 * @author kamingpan
 * @since 2018-05-21
 */
@Data
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundNotifyReqInfo {

    // 微信订单号
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户系统内部的订单号
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 微信退款单号
    @XmlElement(name = "refund_id")
    private String refundId;

    // 商户退款单号
    @XmlElement(name = "out_refund_no")
    private String outRefundNo;

    // 订单金额（订单总金额，单位为分，只能为整数）
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 应结订单金额（去掉非充值代金券金额后的订单总金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额）
    @XmlElement(name = "settlement_total_fee")
    private Long settlementTotalFee;

    // 申请退款金额（退款总金额,单位为分）
    @XmlElement(name = "refund_fee")
    private Long refundFee;

    // 退款金额（退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额）
    @XmlElement(name = "settlement_refund_fee")
    private Long settlementRefundFee;

    /**
     * 退款状态
     * <p>
     * SUCCESS-退款成功
     * CHANGE-退款异常
     * REFUNDCLOSE—退款关闭
     */
    @XmlElement(name = "refund_status")
    private String refundStatus;

    // 退款成功时间（资金退款至用户帐号的时间，格式yyyy-MM-dd HH:mm:ss）
    @XmlElement(name = "success_time")
    private String successTime;

    /**
     * 退款入账账户（取当前退款单的退款入账方）
     * <p>
     * 1）退回银行卡：{银行名称}{卡类型}{卡尾号}
     * 2）退回支付用户零钱：支付用户零钱
     * 3）退还商户：商户基本账户/商户结算银行账户
     * 4）退回支付用户零钱通：支付用户零钱通
     */
    @XmlElement(name = "refund_recv_accout")
    private String refundRecvAccount;

    /**
     * 退款资金来源
     * <p>
     * REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款/基本账户
     * REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款
     */
    @XmlElement(name = "refund_account")
    private String refundAccount;

    /**
     * 退款发起来源
     * <p>
     * API接口
     * VENDOR_PLATFORM 商户平台
     */
    @XmlElement(name = "refund_request_source")
    private String refundRequestSource;

}
