package cn.decentchina.manager.system.util;


import java.nio.charset.Charset;


/**
 * des加密解密工具
 *
 * @author 唐全成
 */
public class DesUtil {
    public static final Charset CHARSET = Charset.forName("utf-8");

    /**
     * 加密
     *
     * @param srcStr  加密数据
     * @param charset 字符集
     * @param sKey    key
     * @return : java.lang.String
     */
    public static String encrypt(String srcStr, Charset charset, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = Des.encrypt(src, sKey);
        return Des.parseByte2HexStr(buf);
    }

    /**
     * 解密
     *
     * @param hexStr 解密数据
     * @param sKey   key
     * @return : java.lang.String
     * @throws Exception 异常
     */
    public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
        byte[] src = Des.parseHexStr2Byte(hexStr);
        byte[] buf = Des.decrypt(src, sKey);
        return new String(buf, charset);
    }
}