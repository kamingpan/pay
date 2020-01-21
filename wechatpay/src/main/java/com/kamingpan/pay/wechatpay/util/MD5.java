package com.kamingpan.pay.wechatpay.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串MD5加密
 *
 * @author kamingpan
 * @since 2016-03-1
 */
@Slf4j
public class MD5 {

    private final static String[] HEX_DIGITS
            = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return MD5.HEX_DIGITS[d1] + MD5.HEX_DIGITS[d2];
    }

    /**
     * MD5编码
     *
     * @param data 原始字符串
     * @return 经过MD5加密之后的结果
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws UnsupportedEncodingException 编码不支持异常
     */
    public static String encrypt(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(data.getBytes("UTF-8"));

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : messageDigest.digest()) {
            stringBuilder.append(byteToHexString(b));
        }
        return stringBuilder.toString().toUpperCase();
    }
}
