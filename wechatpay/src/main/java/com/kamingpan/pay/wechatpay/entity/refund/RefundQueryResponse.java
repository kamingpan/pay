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
 * 微信支付退款查询响应结果
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class RefundQueryResponse extends BaseResponse {

    // 订单总退款次数（订单总共已发生的部分退款次数，当请求参数传入offset后有返回）
    @XmlElement(name = "total_refund_count")
    private Integer totalRefundCount;

    // 微信订单号
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户系统内部的订单号
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 订单金额（订单总金额，单位为分，只能为整数）
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 应结订单金额（应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额）
    @XmlElement(name = "settlement_total_fee")
    private Long settlementTotalFee;

    // 货币种类（订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "fee_type")
    private String feeType;

    // 现金支付金额（现金支付金额，单位为分，只能为整数）
    @XmlElement(name = "cash_fee")
    private Long cashFee;

    // 退款笔数（退款记录数）
    @XmlElement(name = "refund_count")
    private Integer refundCount;

    // 退款金额（退款总金额,单位为分）
    @XmlElement(name = "refund_fee")
    private Long refundFee;

    // 退款详情（不参与序列化）
    private List<Refund> refunds;

    /**
     * 添加退款详情对象
     *
     * @param refund 退款详情
     */
    public void addRefund(Refund refund) {
        if (null == refund) {
            return;
        }

        if (null == this.refunds) {
            this.refunds = new LinkedList<Refund>();
        }
        this.refunds.add(refund);
    }

    /**
     * 退款详情
     */
    @Data
    public static class Refund {

        // 商户退款单号（商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。）
        private String outRefundNo;

        // 微信退款单号（微信退款单号）
        private String refundId;

        // 退款渠道（ORIGINAL—原路退款，BALANCE—退回到余额，OTHER_BALANCE—原账户异常退到其他余额账户，OTHER_BANKCARD—原银行卡异常退到其他银行卡）
        private String refundChannel;

        // 申请退款金额（退款总金额,单位为分,可以做部分退款）
        private Long refundFee;

        // 退款金额（退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额）
        private Long settlementRefundFee;

        // 退款状态（退款状态：SUCCESS—退款成功，REFUNDCLOSE—退款关闭，PROCESSING—退款处理中，CHANGE—退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款。）
        private String refundStatus;

        // 退款资金来源（REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款/基本账户，REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款）
        private String refundAccount;

        // 退款入账账户（取当前退款单的退款入账方：1）退回银行卡：{银行名称}{卡类型}{卡尾号}，2）退回支付用户零钱:支付用户零钱，3）退还商户:商户基本账户、商户结算银行账户，4）退回支付用户零钱通:支付用户零钱通）
        private String refundReceiveAccount;

        // 退款成功时间（退款成功时间，当退款状态为退款成功时有返回。）
        private String refundSuccessTime;

    }

}
