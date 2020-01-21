package com.kamingpan.pay.wechatpay.entity.order;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConfig;
import com.kamingpan.pay.wechatpay.entity.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;

/**
 * 微信支付统一下单请求参数
 *
 * @author kamingpan
 * @since 2016-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnifiedOrderRequest extends BaseRequest {

    // 商品描述，必须（商品或支付单简要描述，该字段须严格按照规范传递）
    @XmlElement(name = "body")
    private String body;

    // 商品详情，非必须（商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。）
    @XmlElement(name = "detail")
    private String detail;

    // 附加数据，非必须（附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据）
    @XmlElement(name = "attach")
    private String attach;

    // 商户订单号，必须（商户系统内部的订单号,32个字符内、可包含字母）
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    // 货币类型，非必须（符合ISO 4217标准的三位字母代码，默认人民币：CNY）
    @XmlElement(name = "fee_type")
    private String feeType;

    // 总金额，必须（订单总金额，单位为分）
    @XmlElement(name = "total_fee")
    private Long totalFee;

    // 终端IP，必须（APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。）
    @XmlElement(name = "spbill_create_ip")
    private String spBillCreateIp = WeChatPayConfig.getServerIp();

    // 交易起始时间，非必须（订单生成时间，格式为yyyyMMddHHmmss）
    @XmlElement(name = "time_start")
    private String timeStart;

    // 交易结束时间，非必须（订单失效时间，格式为yyyyMMddHHmmss。注意：最短失效时间间隔必须大于5分钟）
    @XmlElement(name = "time_expire")
    private String timeExpire;

    // 商品标记，非必须（商品标记，代金券或立减优惠功能的参数）
    @XmlElement(name = "goods_tag")
    private String goodsTag;

    // 通知地址，必须（接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。）
    @XmlElement(name = "notify_url")
    private String notifyUrl = WeChatPayConfig.getWeChatPayNotifyUrl();

    // 交易类型，必须（支付类型：JSAPI(公众号支付)，NATIVE(扫码支付)，APP(APP支付)）
    @XmlElement(name = "trade_type")
    private String tradeType;

    // 商品ID，非必须（trade_type=NATIVE，此参数必传(此id为二维码中包含的商品ID，商户自行定义。）
    @XmlElement(name = "product_id")
    private String productId;

    // 指定支付方式，非必须（上传此参数no_credit--可限制用户不能使用信用卡支付）
    @XmlElement(name = "limit_pay")
    private String limitPay;

    // 用户标识，非必须（trade_type=JSAPI，此参数必传(用户在商户appid下的唯一标识）
    @XmlElement(name = "openid")
    private String openid;

    // 特约（子）商户用户标识，非必须（trade_type=JSAPI并且服务商和特约商户号组合使用时，此参数必传）
    @XmlElement(name = "sub_openid")
    private String subOpenid;

    public UnifiedOrderRequest() {
        // 如果设置了“交易失效时长”，则赋值交易起始和结束时间
        if (null != WeChatPayConfig.getTimeExpire() && 60 < WeChatPayConfig.getTimeExpire()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            // 设置交易起始时间
            long now = System.currentTimeMillis();
            this.timeStart = simpleDateFormat.format(now);

            // 设置交易结束时间
            long expire = WeChatPayConfig.getTimeExpire();
            expire *= 1000L; // 转换毫秒
            expire = now + expire; // 计算到期时间
            this.timeExpire = simpleDateFormat.format(expire);
        }
    }

}
