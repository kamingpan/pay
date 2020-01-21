package com.kamingpan.pay.wechatpay.enumeration;

/**
 * 支付类型枚举
 *
 * @author kamingpan
 * @since 2018-06-05
 */
public enum TradeTypeEnum {

    /**
     * APP支付
     */
    APP("APP"),

    /**
     * 公众号支付支付
     */
    JS_API("JSAPI"),

    /**
     * 扫码支付
     */
    NATIVE("NATIVE"),

    /**
     * 刷卡支付
     */
    MICRO_PAY("MICROPAY"),

    /**
     * H5支付
     */
    M_WEB("MWEB");

    private String tradeType;

    TradeTypeEnum(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    /**
     * 根据支付类型获取支付类型枚举
     *
     * @param tradeType 支付类型
     * @return 支付类型枚举
     */
    public static TradeTypeEnum getByTradeType(String tradeType) {
        if (null != tradeType && !tradeType.trim().isEmpty()) {
            for (TradeTypeEnum tradeTypeEnum : TradeTypeEnum.values()) {
                if (tradeTypeEnum.getTradeType().equals(tradeType)) {
                    return tradeTypeEnum;
                }
            }
        }

        return null;
    }
}
