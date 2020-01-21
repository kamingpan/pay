package com.kamingpan.pay.wechatpay.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 生成随机字符串
 *
 * @author kamingpan
 * @since 2014-07-18
 */
@Slf4j
public class RandomStringGenerator {

    /**
     * 默认长度
     */
    public static final int DEFAULT_LENGTH = 32;

    /**
     * 基础字符串
     */
    private static final String BASE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 获取32位长度的随机字符串
     *
     * @return 32位长度的字符串
     */
    public static String getRandomString() {
        return RandomStringGenerator.getRandomStringByLength(RandomStringGenerator.DEFAULT_LENGTH);
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(RandomStringGenerator.BASE_STRING.length());
            stringBuilder.append(RandomStringGenerator.BASE_STRING.charAt(number));
        }

        return stringBuilder.toString();
    }

}
