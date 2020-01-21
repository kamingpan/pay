package com.kamingpan.pay.wechatpay.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES256ECB加解密
 *
 * @author kamingpan
 * @since 2018-05-21
 */
@Slf4j
public class AES256ECB {

    /**
     * 密钥算法
     */
    private static final String ENCODING = "UTF-8";

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";

    /**
     * Base64解码器
     */
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    /**
     * base64加密器
     */
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    /**
     * AES加密
     *
     * @param data 加密数据
     * @param key  签名密钥
     * @return 加密结果
     * @throws NoSuchPaddingException       填充不存在异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws InvalidKeyException          密钥无效异常
     * @throws BadPaddingException          填充错误异常
     * @throws IllegalBlockSizeException    非法块大小异常
     * @throws UnsupportedEncodingException 编码不支持异常
     */
    public static String encrypt(String data, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (null == key || key.isEmpty()) {
            throw new NullPointerException("签名密钥不能为空");
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(MD5.encrypt(key).toLowerCase().getBytes(), ALGORITHM);

        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return new String(AES256ECB.ENCODER.encode(cipher.doFinal(data.getBytes())), AES256ECB.ENCODING);
    }

    /**
     * AES解密
     *
     * @param data 解密数据
     * @param key  签名密钥
     * @return 解密结果
     * @throws NoSuchPaddingException       填充不存在异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws InvalidKeyException          密钥无效异常
     * @throws BadPaddingException          填充错误异常
     * @throws IllegalBlockSizeException    非法块大小异常
     * @throws UnsupportedEncodingException 编码不支持异常
     */
    public static String decrypt(String data, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        if (null == key || key.isEmpty()) {
            throw new NullPointerException("签名密钥不能为空");
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(MD5.encrypt(key).toLowerCase().getBytes(), ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(AES256ECB.DECODER.decode(data)), AES256ECB.ENCODING);
    }

}
