package com.kamingpan.pay.wechatpay.entity.refund;

import com.kamingpan.pay.wechatpay.entity.base.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * 微信支付申请退款响应结果
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundResponse extends BaseResponse {

    // 微信订单号
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户订单号
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 商户退款单号
    @XmlElement(name = "out_refund_no")
    private String outRefundNo;

    // 微信退款单号
    @XmlElement(name = "refund_id")
    private String refundId;

    // 退款渠道（ORIGINAL—原路退款，BALANCE—退回到余额）
    @XmlElement(name = "refund_channel")
    private String refundChannel;

    // 申请退款金额（退款总金额,单位为分,可以做部分退款）
    @XmlElement(name = "refund_fee")
    private Long refundFee;

    // 退款金额（去掉非充值代金券退款金额后的退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额）
    @XmlElement(name = "settlement_refund_fee")
    private Long settlementRefundFee;

    // 订单金额（订单总金额，单位为分，只能为整数）
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 应结订单金额（去掉非充值代金券金额后的订单总金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额）
    @XmlElement(name = "settlement_total_fee")
    private Long settlementTotalFee;

    // 订单金额货币种类（订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "fee_type")
    private String feeType;

    // 现金支付金额（现金支付金额，单位为分，只能为整数）
    @XmlElement(name = "cash_fee")
    private Long cashFee;

    // 现金支付币种（货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "cash_fee_type")
    private Long cashFeeType;

    // 现金退款金额（现金退款金额，单位为分，只能为整数）
    @XmlElement(name = "cash_refund_fee")
    private Long cashRefundFee;

    // 代金券退款总金额（代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金）
    @XmlElement(name = "coupon_refund_fee")
    private Long couponRefundFee;

    // 退款代金券使用数量（退款代金券使用数量）
    @XmlElement(name = "coupon_refund_count")
    private Long couponRefundCount;

    // 退款详情（不参与序列化）
    private List<Coupon> coupons;

    /**
     * 添加代金券详情对象
     *
     * @param coupon 代金券详情
     */
    public void addCoupon(Coupon coupon) {
        if (null == coupon) {
            return;
        }

        if (null == this.coupons) {
            this.coupons = new LinkedList<Coupon>();
        }
        this.coupons.add(coupon);
    }

    /**
     * 退款详情
     */
    @Data
    public static class Coupon {

        // CASH--充值代金券，NO_CASH---非充值代金券，订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
        private String couponType;

        // 代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金
        private Long couponRefundFee;

        // 退款代金券ID, $n为下标，从0开始编号
        private String couponRefundId;

    }

}
