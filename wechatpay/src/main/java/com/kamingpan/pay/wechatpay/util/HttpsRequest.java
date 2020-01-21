package com.kamingpan.pay.wechatpay.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * 提交http(s)请求
 *
 * @author KM
 * @since 2014-07-18
 */
@Slf4j
public class HttpsRequest {

    // 表示请求器是否已经做了初始化工作
    private static boolean hasInit = false;

    // 连接超时时间，默认10秒
    private static int socketTimeout = 10000;

    // 传输超时时间，默认30秒
    private static int connectTimeout = 30000;

    // 请求器的配置
    private static RequestConfig requestConfig;

    // HTTP请求器
    private static CloseableHttpClient httpClient;

    public HttpsRequest() throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, IOException {
        init();
    }

    private static void init() throws IOException, KeyStoreException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyManagementException {
        httpClient = HttpClients.custom().build();

        // 根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    /**
     * 通过Https post xml数据
     *
     * @param url 请求地址
     * @param xml 提交的xml
     * @return 相应数据
     * @throws IOException               io异常
     * @throws KeyStoreException         密钥库异常
     * @throws UnrecoverableKeyException 不可恢复密钥异常
     * @throws NoSuchAlgorithmException  算法不存在异常
     * @throws KeyManagementException    密钥管理异常
     */
    public static String sendPost(String url, String xml) throws IOException, KeyStoreException,
            UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        if (!hasInit) {
            init();
        }

        HttpPost httpPost = new HttpPost(url);

        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xml, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        // 设置请求器的配置
        httpPost.setConfig(requestConfig);

        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "UTF-8");

        httpPost.abort();

        return result;
    }

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        HttpsRequest.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        HttpsRequest.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig() {
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        HttpsRequest.requestConfig = requestConfig;
    }
}
