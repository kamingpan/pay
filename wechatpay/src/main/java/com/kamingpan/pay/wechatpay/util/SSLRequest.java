package com.kamingpan.pay.wechatpay.util;

import com.kamingpan.pay.wechatpay.exception.WeChatPayException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * SSL请求
 *
 * @author kamingpan
 * @since 2016-12-04
 */
public class SSLRequest {

    /**
     * 根据证书文件路径获取证书文件输入流
     *
     * @param certificate 证书文件路径
     * @return 证书文件输入流
     * @throws WeChatPayException 微信支付异常
     */
    public static InputStream getCertificateInputStream(String certificate) throws WeChatPayException {
        if (null == certificate || certificate.isEmpty()) {
            throw new WeChatPayException("微信支付证书路径不能为空");
        }

        // 根据路径获取微信证书文件（先从jar包内获取，如果不存在，再从服务器绝对路径获取）
        InputStream certificateInputStream = SSLRequest.class.getResourceAsStream(certificate);
        if (null == certificateInputStream) {
            try {
                certificateInputStream = new FileInputStream(certificate);
            } catch (FileNotFoundException exception) {
                throw new WeChatPayException(certificate + " 文件不存在！", exception);
            }
        }

        return certificateInputStream;
    }

    /**
     * 发送SSL请求
     *
     * @param url         请求地址
     * @param xml         提交xml字符串
     * @param certificate 证书文件
     * @param mchId       微信支付分配的商户号
     * @return 响应结果
     * @throws KeyStoreException         密钥库异常
     * @throws IOException               io异常
     * @throws CertificateException      证书异常
     * @throws NoSuchAlgorithmException  算法不存在异常
     * @throws UnrecoverableKeyException 不可恢复密钥异常
     * @throws KeyManagementException    密钥管理异常
     */
    public static String sendRequest(String url, String xml, InputStream certificate, String mchId)
            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyManagementException {
        if (null == certificate) {
            throw new IOException("微信证书不能为空！");
        }

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        char[] keyPassword = mchId.toCharArray();

        keyStore.load(certificate, keyPassword);
        certificate.close();

        // Trust own CA and all self-signed certs
        SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, keyPassword).build();

        // Allow TLSv1 protocol only
        // 过时备份 SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        // httpclient初始化
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();

        HttpPost httpPost = new HttpPost(url);

        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xml, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        CloseableHttpResponse response = httpclient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");

        EntityUtils.consume(entity);
        response.close();
        httpclient.close();

        return result;
    }

}
