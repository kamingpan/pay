package com.kamingpan.pay.wechatpay.entity.order;

import lombok.Data;

/**
 * 商品详情
 *
 * @author kamingpan
 * @since 2016-12-05
 */
@Data
public class GoodsDetail {

    // 商品的编号，必须，32
    private String goodsId;

    // 微信支付定义的统一商品编号，可选，32
    private String wxPayGoodsId;

    // 商品名称，必须，256
    private String goodsName;

    // 商品数量，必填
    private Integer quantity;

    // 商品单价，必填，单位为分
    private Long price;

    // 商品类目ID，可选，32
    private String goodsCategory;

    // 商品描述信息，可选，1000
    private String body;

}
