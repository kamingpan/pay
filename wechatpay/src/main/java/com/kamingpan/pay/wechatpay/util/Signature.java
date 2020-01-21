package com.kamingpan.pay.wechatpay.util;

import com.kamingpan.pay.wechatpay.constant.WeChatPayConstant;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 微信支付签名
 *
 * @author kamingpan
 * @since 2016-07-18
 */
@Slf4j
public class Signature {

    /**
     * 签名算法
     *
     * @param object 要参与签名的数据对象
     * @param key    密钥
     * @return 签名
     * @throws InvalidKeyException          无效键异常
     * @throws IllegalAccessException       非法访问异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public static String getSign(Object object, String key) throws InvalidKeyException,
            IllegalAccessException, NoSuchAlgorithmException, UnsupportedEncodingException {
        return Signature.getSign(object, key, WeChatPayConstant.SignType.MD5);
    }

    /**
     * 签名算法
     *
     * @param xml 要签名的微信响应结果
     * @param key 密钥
     * @return 签名
     * @throws IOException                  IO异常
     * @throws SAXException                 SAX异常
     * @throws InvalidKeyException          无效键异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws ParserConfigurationException 分析器配置异常
     */
    public static String getSign(String xml, String key) throws IOException, SAXException, InvalidKeyException,
            NoSuchAlgorithmException, ParserConfigurationException {
        return Signature.getSign(xml, key, WeChatPayConstant.SignType.MD5);
    }

    /**
     * 签名算法
     *
     * @param object   要参与签名的数据对象
     * @param key      密钥
     * @param singType 加密方式（目前支持HMAC-SHA256和MD5）
     * @return 签名
     * @throws InvalidKeyException          无效键异常
     * @throws IllegalAccessException       非法访问异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public static String getSign(Object object, String key, String singType) throws InvalidKeyException,
            IllegalAccessException, NoSuchAlgorithmException, UnsupportedEncodingException {
        List<String> list = new LinkedList<String>();

        // 获取当前类属性集合
        Class clazz = object.getClass();
        Signature.addField(list, object, clazz);

        // 获取当前类父类属性集合
        clazz = clazz.getSuperclass();
        Signature.addField(list, object, clazz);

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(arrayToSort[i]);
        }
        stringBuilder.append("key=").append(key);

        // 加密，生成sign字符串
        String result = stringBuilder.toString();
        if (WeChatPayConstant.SignType.HMAC_SHA256.equals(singType)) {
            log.debug("Sign Before HMAC-SHA256: {}", result);
            result = HmacSHA256.encrypt(result, key).toUpperCase();
            log.debug("Sign Result: {}", result);
        } else {
            log.debug("Sign Before MD5: {}", result);
            result = MD5.encrypt(result).toUpperCase();
            log.debug("Sign Result: {}", result);
        }
        return result;
    }

    /**
     * 签名算法
     *
     * @param map      要参与签名的数据对象
     * @param key      密钥
     * @param singType 加密方式（目前支持HMAC-SHA256和MD5）
     * @return 签名
     * @throws InvalidKeyException          无效键异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public static String getSign(Map<String, String> map, String key, String singType)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        // 定义StringBuilder保存拼装结果
        StringBuilder stringBuilder = new StringBuilder();

        // 判断传入是否TreeMap，如果是TreeMap，则直接遍历获取即可
        if (TreeMap.class.equals(map.getClass())) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                // id 和 sign 字段不参与加密签名；值为空不参与签名
                if ("sign".equals(entry.getKey()) || "id".equals(entry.getKey())
                        || null == entry.getValue() || entry.getValue().isEmpty()) {
                    continue;
                }
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        } else {
            // 如果不是TreeMap，则将遍历数据放到LinkedList后再排序
            List<String> list = new LinkedList<String>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                // id 和 sign 字段不参与加密签名；值为空不参与签名
                if ("sign".equals(entry.getKey()) || "id".equals(entry.getKey())
                        || null == entry.getValue() || entry.getValue().isEmpty()) {
                    continue;
                }
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
            int size = list.size();
            String[] arrayToSort = list.toArray(new String[size]);
            Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
            for (int i = 0; i < size; i++) {
                stringBuilder.append(arrayToSort[i]);
            }
        }
        stringBuilder.append("key=").append(key);

        // 加密，生成sign字符串
        String result = stringBuilder.toString();
        if (WeChatPayConstant.SignType.HMAC_SHA256.equals(singType)) {
            log.debug("Sign Before HMAC-SHA256: {}", result);
            result = HmacSHA256.encrypt(result, key).toUpperCase();
            log.debug("Sign Result: {}", result);
        } else {
            log.debug("Sign Before MD5: {}", result);
            result = MD5.encrypt(result).toUpperCase();
            log.debug("Sign Result: {}", result);
        }

        return result;
    }

    /**
     * 签名算法
     *
     * @param xml      要签名的微信响应字符串
     * @param key      密钥
     * @param singType 加密方式（目前支持HMAC-SHA256和MD5）
     * @return 签名
     * @throws IOException                  IO异常
     * @throws SAXException                 SAX异常
     * @throws InvalidKeyException          无效键异常
     * @throws NoSuchAlgorithmException     算法不存在异常
     * @throws ParserConfigurationException 分析器配置异常
     */
    public static String getSign(String xml, String key, String singType) throws IOException, SAXException,
            InvalidKeyException, NoSuchAlgorithmException, ParserConfigurationException {
        // 将xml转换成TreeMap
        Map<String, String> map = XMLConversion.convertToMap(xml);
        return Signature.getSign(map, key, singType);
    }

    /**
     * 属性值添加至列表中
     *
     * @param list   属性列表
     * @param object 对象
     * @param clazz  类
     * @throws IllegalAccessException 非法访问异常
     */
    private static void addField(List<String> list, Object object, Class clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // id 和 sign 字段不参与加密签名
            if ("sign".equals(field.getName()) || "id".equals(field.getName())) {
                continue;
            }

            field.setAccessible(true);
            String value = null == field.get(object) ? null : field.get(object).toString();
            // 空值和空字符串不参与加密签名
            if (null == value || value.trim().isEmpty()) {
                continue;
            }
            XmlElement xmlElement = field.getAnnotation(XmlElement.class);
            if (null != xmlElement) {
                list.add(xmlElement.name() + "=" + value + "&");
            } else {
                list.add(field.getName() + "=" + value + "&");
            }
        }
    }

}
