package cn.decentchina.manager.system.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author 唐全成
 */
public class RsaUtil {

    /**
     * BASE64解密
     *
     * @param key 源数据
     * @return : byte[]
     * @throws Exception 异常
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key 源数据
     * @return : java.lang.String
     * @throws Exception 异常
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param data       源数据
     * @return : java.lang.String
     * @throws Exception 异常
     */
    public static String decrypt(RSAPrivateKey privateKey, String data) throws Exception {
        return new String(decryptByPrivateKey(decryptBASE64(data), encryptBASE64(privateKey.getEncoded())), StandardCharsets.UTF_8.name());
    }

    /**
     * 解密
     *
     * @param data 源数据
     * @param key  解密前的私钥
     * @return : byte[]
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
}