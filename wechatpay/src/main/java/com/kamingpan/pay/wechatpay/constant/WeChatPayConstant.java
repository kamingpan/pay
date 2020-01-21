package com.kamingpan.pay.wechatpay.constant;

/**
 * 微信支付基础参数
 *
 * @author kamingpan
 * @since 2016-05-04
 */
public class WeChatPayConstant {

    /**
     * 请求地址
     */
    public static final class RequestURL {
        /**
         * 统一下单地址
         */
        public static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        /**
         * 查询订单地址
         */
        public static final String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
        /**
         * 关闭订单地址
         */
        public static final String CLOSE_ORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
        /**
         * 申请退款地址
         */
        public static final String REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        /**
         * 查询退款地址
         */
        public static final String REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
        /**
         * 刷卡支付提交地址
         */
        public static final String MICRO_PAY = "https://api.mch.weixin.qq.com/pay/micropay";
        /**
         * 撤销订单地址（刷卡支付专用）
         */
        public static final String REVERSE_ORDER = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
        /**
         * 下载对账单地址
         */
        public static final String DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/pay/downloadbill";
        /**
         * 测速上报地址
         */
        public static final String REPORT = "https://api.mch.weixin.qq.com/payitil/report";
    }

    /**
     * 请求测试地址
     */
    public static final class SANDBOX_URL {
        /**
         * 统一下单地址
         */
        public static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
        /**
         * 查询订单地址
         */
        public static final String ORDER_QUERY = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";
        /**
         * 关闭订单地址
         */
        public static final String CLOSE_ORDER = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";
        /**
         * 申请退款地址
         */
        public static final String REFUND = "https://api.mch.weixin.qq.com/secapi/sandboxnew/pay/refund";
        /**
         * 查询退款地址
         */
        public static final String REFUND_QUERY = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";
        /**
         * 刷卡支付提交地址
         */
        public static final String MICRO_PAY = "https://api.mch.weixin.qq.com/sandboxnew/pay/micropay";
        /**
         * 撤销订单地址（刷卡支付专用）
         */
        public static final String REVERSE_ORDER = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/reverse";
        /**
         * 获取沙箱版密钥地址
         */
        public static final String SANDBOX_SIGN_KEY = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        /**
         * 下载对账单地址
         */
        public static final String DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";
        /**
         * 测速上报地址
         */
        public static final String REPORT = "https://api.mch.weixin.qq.com/sandboxnew/payitil/report";
    }


    /**
     * 提交参数device_info(设备号)
     */
    public static final class RequestParameterDeviceInfo {
        /**
         * WEB(APP支付，PC网页或公众号内支付请传"WEB")
         */
        public static final String WEB = "WEB";
    }


    /**
     * 微信支付请求签名类型
     */
    public static final class SignType {
        /**
         * md5
         */
        public static final String MD5 = "MD5";
        /**
         * HMAC-SHA256
         */
        public static final String HMAC_SHA256 = "HMAC-SHA256";
    }


    /**
     * 提交参数trade_type(交易类型)
     */
    public static final class TradeType {
        /**
         * APP(app支付)
         */
        public static final String APP = "APP";
        /**
         * JSAPI(公众号支付)
         */
        public static final String JS_API = "JSAPI";
        /**
         * NATIVE(原生扫码支付)
         */
        public static final String NATIVE = "NATIVE";
        /**
         * MICROPAY(刷卡支付)
         */
        public static final String MICRO_PAY = "MICROPAY";
        /**
         * MWEB(H5支付)
         */
        public static final String M_WEB = "MWEB";
    }


    /**
     * 提交参数fee_type(货币类型)
     */
    public static final class FeeType {
        /**
         * CNY(人民币)
         */
        public static final String CNY = "CNY";
    }


    /**
     * 提交参数limit_pay(指定支付方式)
     */
    public static final class LimitPay {
        /**
         * no_credit(指定不能使用信用卡支付)
         */
        public static final String NO_CREDIT = "no_credit";
    }


    /**
     * 微信支付响应 状态码或业务 结果
     */
    public static final class ResponseCode {
        /**
         * 微信支付响应 状态码或业务 结果—成功
         */
        public static final String SUCCESS = "SUCCESS";
        /**
         * 微信支付响应 状态码或业务 结果—失败
         */
        public static final String FAIL = "FAIL";
    }


    /**
     * 微信支付响应结果状态
     */
    public static final class ResponseStatus {

        /**
         * 微信支付响应状态—成功
         */
        public static final String RETURN_SUCCESS = "SUCCESS";

        /**
         * 微信支付响应状态—失败
         */
        public static final String RETURN_FAIL = "FAIL";

        /**
         * 微信支付业务结果状态—成功
         */
        public static final String RESULT_SUCCESS = "SUCCESS";

        /**
         * 微信支付业务结果状态—失败
         */
        public static final String RESULT_FAIL = "FAIL";

    }


    /**
     * 订单查询交易状态
     */
    public static final class OrderQueryTradeState {
        /**
         * 订单查询交易状态—支付成功
         */
        public static final String SUCCESS = "SUCCESS";
        /**
         * 订单查询交易状态—转入退款
         */
        public static final String REFUND = "REFUND";
        /**
         * 订单查询交易状态—未支付
         */
        public static final String NOT_PAY = "NOTPAY";
        /**
         * 订单查询交易状态—已关闭
         */
        public static final String CLOSED = "CLOSED";
        /**
         * 订单查询交易状态—已撤销(刷卡支付)
         */
        public static final String REVOKED = "REVOKED";
        /**
         * 订单查询交易状态—用户支付中
         */
        public static final String USER_PAYING = "USERPAYING";
        /**
         * 订单查询交易状态—支付失败(其他原因，如银行返回失败)
         */
        public static final String PAY_ERROR = "PAYERROR";
    }


    /**
     * 查询退款退款状态
     */
    public static final class RefundQueryState {
        /**
         * 查询退款退款状态—退款成功
         */
        public static final String SUCCESS = "SUCCESS";
        /**
         * 查询退款退款状态—退款失败
         */
        public static final String FAIL = "FAIL";
        /**
         * 查询退款退款状态—退款处理中
         */
        public static final String PROCESSING = "PROCESSING";
        /**
         * 查询退款退款状态—未确定，需要商户原退款单号重新发起
         */
        public static final String NOT_SURE = "NOTSURE";
        /**
         * 查询退款退款状态—转入代发
         */
        public static final String CHANGE = "CHANGE";
        /**
         * 退款关闭
         */
        public static final String CLOSE = "REFUNDCLOSE";
    }

}
