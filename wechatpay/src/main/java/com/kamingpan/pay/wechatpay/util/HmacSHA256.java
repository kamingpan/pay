package com.kamingpan.pay.wechatpay.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HmacSHA256加密
 *
 * @author kamingpan
 * @since 2018-05-21
 */
@Slf4j
public class HmacSHA256 {

    /**
     * HMAC-SHA256加密
     *
     * @param data 数据
     * @param key  密钥
     * @return 加密结果（转换大写）
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws UnsupportedEncodingException 编码不支持异常
     * @throws InvalidKeyException          密钥无效异常
     */
    public static String encrypt(String data, String key)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256HMAC.init(secret_key);

        byte[] array = sha256HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : array) {
            stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }

        return stringBuilder.toString().toUpperCase();
    }

}
