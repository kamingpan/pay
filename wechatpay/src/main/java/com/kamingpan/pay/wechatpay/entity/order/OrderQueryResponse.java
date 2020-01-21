package com.kamingpan.pay.wechatpay.entity.order;

import com.kamingpan.pay.wechatpay.entity.base.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付订单查询响应结果
 *
 * @author kamingpan
 * @since 2016-12-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderQueryResponse extends BaseResponse {

    // 用户标识（用户在商户appid下的唯一标识）
    @XmlElement(name = "openid")
    private String openid;

    // 是否关注公众账号（用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效）
    @XmlElement(name = "is_subscribe")
    private String isSubscribe;

    // 交易类型（调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY）
    @XmlElement(name = "trade_type")
    private String tradeType;

    // 交易状态（SUCCESS—支付成功，REFUND—转入退款，NOTPAY—未支付，CLOSED—已关闭，REVOKED—已撤销（刷卡支付），USERPAYING--用户支付中，PAYERROR--支付失败（其他原因，如银行返回失败））
    @XmlElement(name = "trade_state")
    private String tradeState;

    // 付款银行（银行类型，采用字符串类型的银行标识）
    @XmlElement(name = "bank_type")
    private String bankType;

    // 订单金额（订单总金额，单位为分）
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 应结订单金额（应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额）
    @XmlElement(name = "settlement_total_fee")
    private Long settlementTotalFee;

    // 货币种类（货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "fee_type")
    private String feeType;

    // 现金支付金额（现金支付金额订单现金支付金额）
    @XmlElement(name = "cash_fee")
    private Long cashFee;

    // 现金支付货币类型（货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "cash_fee_type")
    private String cashFeeType;

    // 代金券金额（“代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额）
    @XmlElement(name = "coupon_fee")
    private Long couponFee;

    // 代金券使用数量（代金券使用数量）
    @XmlElement(name = "coupon_count")
    private Integer couponCount;

    // 微信支付订单号
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户订单号（商户系统的订单号，与请求一致）
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 附加数据（附加数据，原样返回）
    @XmlElement(name = "attach")
    private String attach;

    // 支付完成时间（订单支付时间，格式为yyyyMMddHHmmss）
    @XmlElement(name = "time_end")
    private String timeEnd;

    // 交易状态描述（对当前查询订单状态的描述和下一步操作的指引）
    @XmlElement(name = "trade_state_desc")
    private String tradeStateDesc;

}
