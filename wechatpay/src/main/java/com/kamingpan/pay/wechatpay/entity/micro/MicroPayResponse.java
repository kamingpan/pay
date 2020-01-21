package com.kamingpan.pay.wechatpay.entity.micro;

import com.kamingpan.pay.wechatpay.entity.base.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信刷卡支付响应结果
 *
 * @author kamingpan
 * @since 2016-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MicroPayResponse extends BaseResponse {

    // 用户标识(用户在商户appid 下的唯一标识)
    @XmlElement(name = "openid")
    private String openid;

    // 是否关注公众账号(用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注)
    @XmlElement(name = "is_subscribe")
    private String isSubscribe;

    // 交易类型(支付类型为MICROPAY(即扫码支付))
    @XmlElement(name = "trade_type")
    private String tradeType;

    // 付款银行(银行类型，采用字符串类型的银行标识)
    @XmlElement(name = "bank_type")
    private String bankType;

    // 货币类型(默认人民币：CNY)
    @XmlElement(name = "fee_type")
    private String feeType;

    // 订单金额(订单总金额，单位为分，只能为整数)
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 应结订单金额(应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。)
    @XmlElement(name = "settlement_total_fee")
    private Long settlementTotalFee;

    // 代金券金额(“代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额)
    @XmlElement(name = "coupon_fee")
    private Long couponFee;

    // 现金支付货币类型(默认人民币：CNY)
    @XmlElement(name = "cash_fee_type")
    private String cashFeeType;

    // 现金支付金额(订单现金支付金额)
    @XmlElement(name = "cash_fee")
    private Long cashFee;

    // 微信支付订单号
    @XmlElement(name = "transaction_id")
    private String transactionId;

    // 商户订单号(商户系统的订单号，与请求一致)
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 商家数据包(商家数据包，原样返回)
    @XmlElement(name = "attach")
    private String attach;

    // 支付完成时间(订单生成时间，格式为yyyyMMddHHmmss)
    @XmlElement(name = "time_end")
    private String timeEnd;

    // 营销详情(订单生成时间，单品优惠功能字段)
    @XmlElement(name = "promotion_detail")
    private String promotionDetail;

}
