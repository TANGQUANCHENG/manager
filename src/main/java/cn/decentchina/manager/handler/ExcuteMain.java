package cn.decentchina.manager.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.SocketTimeoutException;

/**
 * @author 唐全成
 * @date 2018-12-13
 */
@Slf4j
public class ExcuteMain {
    public static void main(String[] args) throws InterruptedException {

        String[] ids = {"46271",
                "46463",
                "46625",
                "46777",
                "46778",
                "46889",
                "46890",
                "46891",
                "46913",
                "46920",
                "46981",
                "47018",
                "47083",
                "47114",
                "47116",
                "47118",
                "47126",
                "47187",
                "47190",
                "47292",
                "47527",
                "47578",
                "47602",
                "47629",
                "47658",
                "47675",
                "47679",
                "47689",
                "47702",
                "47718",
                "48034",
                "48039",
                "48108",
                "48231",
                "48244",
                "48280",
                "48281",
                "48337",
                "48340",
                "48349",
                "48366",
                "48367",
                "48379",
                "48394",
                "48430",
                "48447",
                "48451",
                "48453",
                "48455",
                "48459"};

        for (String id : ids) {
            String url = "http://47.93.83.62:65432/ticket/setSuccess/"+id+"?sellingPrice=38&distributionChannel=闲鱼1&voucherValue=0&remark=12.17给乔哥，收款账户：公总";
            String result = get(url, 20000, 20000);
            log.info("id:[{}]返回结果：[{}]", id, result);
            Thread.sleep(500);
        }
    }

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
            httpGet.addHeader("Cookie", "JSESSIONID=4d235b53-510a-49fe-9f5b-0cdc504ac6c7");
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
}
