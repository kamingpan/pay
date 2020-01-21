package com.kamingpan.pay.wechatpay.service;

import com.kamingpan.pay.wechatpay.entity.sandbox.SignKeyResponse;
import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import com.kamingpan.pay.wechatpay.service.sandbox.WeChatPaySandboxService;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付沙箱业务单元测试
 *
 * @author kamingpan
 * @since 2018-06-01
 */
@Slf4j
public class WeChatPaySandboxServiceTest {

    // 微信支付沙箱业务
    private WeChatPaySandboxService weChatPaySandboxService = new WeChatPaySandboxService();

    /**
     * 获取沙箱版签名密钥单元测试
     *
     * @throws WeChatPayException 微信支付异常
     */
    // @Test
    public void getSignKeyTest() throws WeChatPayException {
        SignKeyResponse signKeyResponse = weChatPaySandboxService.getSignKey();
        System.out.println(signKeyResponse);
    }

}
