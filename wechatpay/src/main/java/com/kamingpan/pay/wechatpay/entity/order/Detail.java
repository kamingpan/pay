package com.kamingpan.pay.wechatpay.entity.order;

import lombok.Data;

import java.util.List;

/**
 * 商品详情列表（统一下单参数）
 *
 * @author kamingpan
 * @since 2016-12-05
 */
@Data
public class Detail {

    private List<GoodsDetail> goodsDetails;

}
