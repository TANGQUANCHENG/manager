package cn.decentchina.manager.system.util;

import cn.decentchina.manager.system.config.Constants;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author 唐全成
 * @date 2018/2/12
 */
public class SecurityUtil {

    public static Map<String ,Object > getKeyMap() throws Exception {

        Map<String ,Object> map=new HashMap<>(4);
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        map.put(Constants.PUBLIC_KEY, publicKey);

        map.put(Constants.PRIVATE_KEY, privateKey);

        map.put(Constants.RANDOM_STR,getRandomStr(8));
        return map;
    }
    /**
     * 获取长度为n的随机字符串
     * @param length
     * @return
     */
    public static String getRandomStr(int length){

        Long timestamp=System.currentTimeMillis();
        StringBuilder sb=new StringBuilder(20);
        sb.append(timestamp).append(Math.random());
        String origin= MD5.crypt(sb.toString());

        return origin.substring(0,length);
    }
}
