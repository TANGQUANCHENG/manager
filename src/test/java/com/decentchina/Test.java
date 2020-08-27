package com.decentchina;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * @author 唐全成
 * @date 2018-12-24
 */

@Slf4j
public class Test {

    private static final String APPID = "wx1fc60ec54342a85e";// 微信应用唯一标识
    private static final String SECRET = "7a7e1f8b21b3422afdc1bd119745d11a";

    public static void main(String code,String encryptedData,String iv) throws JSONException {

        JSONObject jsonObject = code2sessionKey(code);

        String openId = jsonObject.getString("openid");// 用户唯一标识

        String session_key = jsonObject.getString("session_key");// 密钥

        // 解密encryptedData,获取unionId相关信息
    }

    public static void main(String[] args) {
        String code="023TPDF30DYuYJ1cXAE30T2kF30TPDFX";
        String encryptedData="j4qF8gGqqzYZBhiTDnUFfjCeMktlEqGPa4jEuxeM61UNfz6ZcHX5Aa2IkQp2uN7LF7llEFZke1f1wwM5B3th4vRTWylltcw+i76FiQC5mXvxwY3StjE3D0RqNlfOO7WUKgKiMWro06zcbD+y2ynx7VT/ouX2dpoirq18rAswWHKSzSb9E1lsvnHcS3XZna3FM7dxypXTF1/jDb3rLTU5eI4OQE3DjhMsXmUcLUmSBR1SGD4/uk2+GeEzHxw7KRs4V/lQn2BTh3QVB/b8+PS/Sl49jJz7mf3voS7473HpDycW1aoyMoniP/gXwjyC8HM80wbGN3XqoO+t9OLCTjWggFyQvwsQcPnIM2WYrHWjx7Q3LeDHA2I+8f4suwNH7bSFnjLpvzYl8O8vswOqHzGupGNDypMDD7pmziACRkUriD2erlLstMYpzjIEreUhE9O0tKI8xw3dxriNgBRN+cMcVnh7gihOlSiYIJLNrBibHy9a4YNhWLlVpy2BvHY6hQ6Za3u7l6ie4vDqiqN2q12nWw==";
        String iv="Nry425xuQJ7T02bravoKoA==";
        main(code,encryptedData,iv);
    }

    /**
     * 发送请求用code换取sessionKey和相关信息
     *
     * @param code
     * @return
     */
    public static JSONObject code2sessionKey(String code) {
        String stringToken = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                APPID, SECRET, code);
        String response = httpsRequestToString(stringToken, "GET", null);
        return JSON.parseObject(response);
    }

    /**
     * 发送https请求
     *
     * @param path
     * @param method
     * @param body
     * @return
     */
    public static String httpsRequestToString(String path, String method, String body) {
        if (path == null || method == null) {
            return null;
        }
        String response = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        HttpsURLConnection conn = null;
        try {
            // 创建SSLConrext对象，并使用我们指定的信任管理器初始化
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            TrustManager[] tm = { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

            } };
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上面对象中得到SSLSocketFactory
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(path);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（get|post）
            conn.setRequestMethod(method);

            // 有数据提交时
            if (null != body) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(body.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            response = buffer.toString();
        } catch (Exception e) {

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException execption) {

            }
        }
        return response;
    }
}

