package com.kamingpan.pay.wechatpay.entity.micro;

import com.kamingpan.pay.wechatpay.entity.base.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信刷卡支付撤销订单响应结果
 *
 * @author kamingpan
 * @since 2016-12-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReverseOrderResponse extends BaseResponse {

    // 是否重调（是否需要继续调用撤销，Y-需要，N-不需要）
    @XmlElement(name = "recall")
    private String recall;

}
