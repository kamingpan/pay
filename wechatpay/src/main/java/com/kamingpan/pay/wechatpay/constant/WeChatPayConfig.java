package com.kamingpan.pay.wechatpay.constant;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 微信支付配置文件
 *
 * @author kamingpan
 * @since 2016-12-03
 */
@Slf4j
public final class WeChatPayConfig {

    /**
     * 配置对象
     */
    private static Properties properties;
    /**
     * 配置文件路径
     */
    private static final String CONFIG_FILE = "wechatpay/wechatpay-config.properties";


    /**
     * 微信支付下单回调地址
     */
    private static String weChatPayNotifyUrl = "wechatpay_notify_url";

    /**
     * 微信支付退款回调地址
     */
    private static String weChatPayRefundNotifyUrl = "wechatpay_refund_notify_url";

    /**
     * 公众账号ID || 应用ID
     */
    private static String appId = "app_id";
    /**
     * 微信支付分配的商户号
     */
    private static String mchId = "mch_id";
    /**
     * 微信支付加密密钥（key）
     */
    private static String key = "key";
    /**
     * 签名加密方式（目前支持HMAC-SHA256和MD5，默认为MD5）
     */
    private static String signType = "sign_type";

    /**
     * 是否使用服务商和特约（子）商户号组合
     */
    private static boolean isSubMerchant = false;
    /**
     * 特约（子）商户应用ID
     */
    private static String subAppId = "sub_app_id";
    /**
     * 特约（子）商户号
     */
    private static String subMchId = "sub_mch_id";

    /**
     * 是否使用沙箱（测试）环境
     */
    private static boolean useSandbox = false;

    /**
     * 设备号
     */
    private static String deviceInfo = "device_info";

    /**
     * 系统服务器外网ip
     */
    private static String serverIp = "server_ip";

    /**
     * 订单失效时间
     */
    private static Long timeExpire = null;

    /**
     * SSL通道证书路径
     */
    private static String weChatPayCertificate = "wechatpay_certificate";

    /**
     * 是否使用以下配置地址（如果不使用，则默认使用配置好的正式环境和沙箱环境地址）
     */
    private static boolean useConfigUrl = false;
    /**
     * 统一下单地址
     */
    private static String unifiedOrderUrl = WeChatPayConstant.RequestURL.UNIFIED_ORDER;

    /**
     * 查询订单地址
     */
    private static String orderQueryUrl = WeChatPayConstant.RequestURL.ORDER_QUERY;

    /**
     * 关闭订单地址
     */
    private static String closeOrderUrl = WeChatPayConstant.RequestURL.CLOSE_ORDER;

    /**
     * 申请退款地址
     */
    private static String refundUrl = WeChatPayConstant.RequestURL.REFUND;

    /**
     * 查询退款地址
     */
    private static String refundQueryUrl = WeChatPayConstant.RequestURL.REFUND_QUERY;

    /**
     * 刷卡支付提交地址
     */
    private static String microPayUrl = WeChatPayConstant.RequestURL.MICRO_PAY;

    /**
     * 撤销订单地址（刷卡支付专用）
     */
    private static String reverseOrderUrl = WeChatPayConstant.RequestURL.REVERSE_ORDER;

    /**
     * 获取沙箱版密钥地址
     */
    private static String sandboxSignKeyUrl = WeChatPayConstant.SANDBOX_URL.SANDBOX_SIGN_KEY;

    /**
     * 下载对账单地址
     */
    private static String downloadBillUrl = WeChatPayConstant.RequestURL.DOWNLOAD_BILL;

    /**
     * 测速上报地址
     */
    private static String reportUrl = WeChatPayConstant.RequestURL.REPORT;

    static {
        try {
            properties = new Properties();
            InputStreamReader inputStreamReader = new InputStreamReader(WeChatPayConstant.class.getClassLoader()
                    .getResourceAsStream(CONFIG_FILE), "UTF-8");
            properties.load(inputStreamReader);
            inputStreamReader.close();

            WeChatPayConfig.weChatPayNotifyUrl = properties.getProperty("wechatpay_notify_url");
            WeChatPayConfig.weChatPayRefundNotifyUrl = properties.getProperty("wechatpay_refund_notify_url");
            WeChatPayConfig.appId = properties.getProperty("app_id");
            WeChatPayConfig.mchId = properties.getProperty("mch_id");
            WeChatPayConfig.key = properties.getProperty("key");


            WeChatPayConfig.signType = WeChatPayConstant.SignType.HMAC_SHA256.equals(properties.getProperty("sign_type"))
                    ? WeChatPayConstant.SignType.HMAC_SHA256 : WeChatPayConstant.SignType.MD5;

            WeChatPayConfig.isSubMerchant = "true".equals(properties.getProperty("is_sub_merchant"));
            WeChatPayConfig.subAppId = (null == properties.getProperty("sub_app_id")
                    || properties.getProperty("sub_app_id").isEmpty()) ? null : properties.getProperty("sub_app_id");
            WeChatPayConfig.subMchId = (null == properties.getProperty("sub_mch_id")
                    || properties.getProperty("sub_mch_id").isEmpty()) ? null : properties.getProperty("sub_mch_id");
            WeChatPayConfig.useSandbox = "true".equals(properties.getProperty("use_sandbox"));

            WeChatPayConfig.deviceInfo = properties.getProperty("device_info");
            WeChatPayConfig.serverIp = properties.getProperty("server_ip");

            if (null != properties.getProperty("time_expire") && !properties.getProperty("time_expire").isEmpty()) {
                WeChatPayConfig.timeExpire = Long.parseLong(properties.getProperty("time_expire"));
            }

            WeChatPayConfig.weChatPayCertificate = properties.getProperty("wechatpay_certificate");

            // 判断是否使用配置地址，如果使用，则赋值配置地址
            WeChatPayConfig.useConfigUrl = "true".equals(properties.getProperty("use_config_url"));
            if (WeChatPayConfig.useConfigUrl) {
                if (null != properties.getProperty("unified_order_url")
                        && !properties.getProperty("unified_order_url").isEmpty()) {
                    WeChatPayConfig.unifiedOrderUrl = properties.getProperty("unified_order_url");
                }
                if (null != properties.getProperty("order_query_url")
                        && !properties.getProperty("order_query_url").isEmpty()) {
                    WeChatPayConfig.orderQueryUrl = properties.getProperty("order_query_url");
                }
                if (null != properties.getProperty("close_order_url")
                        && !properties.getProperty("close_order_url").isEmpty()) {
                    WeChatPayConfig.closeOrderUrl = properties.getProperty("close_order_url");
                }
                if (null != properties.getProperty("refund_url")
                        && !properties.getProperty("refund_url").isEmpty()) {
                    WeChatPayConfig.refundUrl = properties.getProperty("refund_url");
                }
                if (null != properties.getProperty("refund_query_url")
                        && !properties.getProperty("refund_query_url").isEmpty()) {
                    WeChatPayConfig.refundQueryUrl = properties.getProperty("refund_query_url");
                }
                if (null != properties.getProperty("micro_pay_url")
                        && !properties.getProperty("micro_pay_url").isEmpty()) {
                    WeChatPayConfig.microPayUrl = properties.getProperty("micro_pay_url");
                }
                if (null != properties.getProperty("reverse_order_url")
                        && !properties.getProperty("reverse_order_url").isEmpty()) {
                    WeChatPayConfig.reverseOrderUrl = properties.getProperty("reverse_order_url");
                }
                if (null != properties.getProperty("sandbox_sign_key_url")
                        && !properties.getProperty("sandbox_sign_key_url").isEmpty()) {
                    WeChatPayConfig.sandboxSignKeyUrl = properties.getProperty("sandbox_sign_key_url");
                }
                if (null != properties.getProperty("download_bill_url")
                        && !properties.getProperty("download_bill_url").isEmpty()) {
                    WeChatPayConfig.downloadBillUrl = properties.getProperty("download_bill_url");
                }
                if (null != properties.getProperty("report_url") && !properties.getProperty("report_url").isEmpty()) {
                    WeChatPayConfig.reportUrl = properties.getProperty("report_url");
                }
            } else if (WeChatPayConfig.useSandbox) {
                // 判断是否使用沙箱地址，如果是，则赋值沙箱环境地址
                WeChatPayConfig.unifiedOrderUrl = WeChatPayConstant.SANDBOX_URL.UNIFIED_ORDER;
                WeChatPayConfig.orderQueryUrl = WeChatPayConstant.SANDBOX_URL.ORDER_QUERY;
                WeChatPayConfig.closeOrderUrl = WeChatPayConstant.SANDBOX_URL.CLOSE_ORDER;
                WeChatPayConfig.refundUrl = WeChatPayConstant.SANDBOX_URL.REFUND;
                WeChatPayConfig.refundQueryUrl = WeChatPayConstant.SANDBOX_URL.REFUND_QUERY;
                WeChatPayConfig.microPayUrl = WeChatPayConstant.SANDBOX_URL.MICRO_PAY;
                WeChatPayConfig.reverseOrderUrl = WeChatPayConstant.SANDBOX_URL.REVERSE_ORDER;
                WeChatPayConfig.downloadBillUrl = WeChatPayConstant.SANDBOX_URL.DOWNLOAD_BILL;
                WeChatPayConfig.reportUrl = WeChatPayConstant.SANDBOX_URL.REPORT;
            }

            log.info("初始化微信支付配置文件参数成功。。。");
        } catch (IOException exception) {
            log.error("初始化微信支付配置文件参数失败", exception);
        }
    }

    public WeChatPayConfig() {
    }

    public static String getWeChatPayNotifyUrl() {
        return weChatPayNotifyUrl;
    }

    public static String getWeChatPayRefundNotifyUrl() {
        return weChatPayRefundNotifyUrl;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getMchId() {
        return mchId;
    }

    public static String getKey() {
        return key;
    }

    public static String getSignType() {
        return signType;
    }

    public static boolean isSubMerchant() {
        return isSubMerchant;
    }

    public static String getSubAppId() {
        return subAppId;
    }

    public static String getSubMchId() {
        return subMchId;
    }

    public static String getDeviceInfo() {
        return deviceInfo;
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static Long getTimeExpire() {
        return timeExpire;
    }

    public static String getWeChatPayCertificate() {
        return weChatPayCertificate;
    }

    public static String getUnifiedOrderUrl() {
        return unifiedOrderUrl;
    }

    public static String getOrderQueryUrl() {
        return orderQueryUrl;
    }

    public static String getCloseOrderUrl() {
        return closeOrderUrl;
    }

    public static String getRefundUrl() {
        return refundUrl;
    }

    public static String getRefundQueryUrl() {
        return refundQueryUrl;
    }

    public static String getMicroPayUrl() {
        return microPayUrl;
    }

    public static String getReverseOrderUrl() {
        return reverseOrderUrl;
    }

    public static String getSandboxSignKeyUrl() {
        return sandboxSignKeyUrl;
    }

    public static String getDownloadBillUrl() {
        return downloadBillUrl;
    }

    public static String getReportUrl() {
        return reportUrl;
    }
}
