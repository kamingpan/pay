package com.kamingpan.pay.wechatpay.entity.order;

import com.kamingpan.pay.wechatpay.entity.base.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信支付统一下单响应结果
 *
 * @author kamingpan
 * @since 2016-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnifiedOrderResponse extends BaseResponse {

    // 交易类型（调用接口提交的交易类型：JSAPI(公众号支付)，NATIVE(扫码支付)，APP(APP支付)）
    @XmlElement(name = "trade_type")
    private String tradeType;

    // 预支付交易会话标识（微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时）
    @XmlElement(name = "prepay_id")
    private String prepayId;

    // 二维码链接（trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付）
    @XmlElement(name = "code_url")
    private String codeUrl;

    // 支付跳转链接（trade_type为MWEB是有返回，mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。）
    @XmlElement(name = "mweb_url")
    private String mWebUrl;

}
