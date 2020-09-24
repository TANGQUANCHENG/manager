package cn.decentchina.manager.system.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * @author 81072
 * http请求工具类
 * Created by 王元鑫 on 2015/7/22.
 */
@Slf4j
public final class HttpClient {
    /****
     * URL GET连接
     *
     * @param url            get访问URL地址
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String get(String url, int connectTimeout, int socketTimeout) {
        String returnStr = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpGet.setConfig(config);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnStr = EntityUtils.toString(entity);
                }
            }
            httpGet.abort();
            return returnStr;
        } catch (SocketTimeoutException e) {
            log.error("地址: {}读取异常: {}", url, e);
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            log.error("地址: {}连接异常: {}", url, e);
            return "";
        } catch (Exception e) {
            log.error("地址: {}请求异常: {}", url, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /****
     * URL POST访问
     *
     * @param url            地址
     * @param params         POST内容
     * @param charset        编码格式
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String post(String url, List<NameValuePair> params, String charset, int connectTimeout, int socketTimeout) {
        String returnStr;

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
            httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(url);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);

            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(config);

            httpResponse = httpClient.execute(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
            StringBuilder stringBuffer = new StringBuilder(100);
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            returnStr = stringBuffer.toString();
            reader.close();
            return returnStr;
        } catch (SocketTimeoutException e) {
            log.error("地址: {}|参数: {} 读取异常: {}", url, params, e);
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            log.error("地址: {}|参数: {} 连接异常: {}", url, params, e);
            return "";
        } catch (Exception e) {
            log.error("地址: {}|参数: {} 请求异常: {}", url, params, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }

    /****
     * URL POST访问
     *
     * @param url            地址
     * @param params         POST内容String字符串
     * @param charset        编码格式
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  读取超时时间
     * @return http返回值
     */
    public static String postString(String url, String params, String charset, int connectTimeout, int socketTimeout) {
        String returnStr;

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            StringEntity entity = new StringEntity(params, charset);
            httpClient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(url);
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);

            RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            httpPost.setConfig(config);

            httpResponse = httpClient.execute(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), charset));
            StringBuilder stringBuffer = new StringBuilder(100);
            String str;

            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            returnStr = stringBuffer.toString();
            reader.close();
            return returnStr;
        } catch (SocketTimeoutException e) {
            log.error("地址: {}|参数: {} 读取异常: {}", url, params, e);
            return "读取超时";
        } catch (ConnectTimeoutException e) {
            log.error("地址: {}|参数: {} 连接异常: {}", url, params, e);
            return "";
        } catch (Exception e) {
            log.error("地址: {}|参数: {} 请求异常: {}", url, params, e);
            return "请求异常";
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
            HttpClientUtils.closeQuietly(httpClient);
        }
    }
}
