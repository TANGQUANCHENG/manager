package cn.decentchina.manager.system.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author 唐全成
 */
@SuppressWarnings("unused")
public class Md5 {
    /**
     * 传入字符串返回字符串
     *
     * @param userName 源数据
     * @return : java.lang.String
     */
    public static String crypt(String userName) {
        return DigestUtils.md5Hex(userName);
    }

    /**
     * 传入参数集map 按照key名，字母的升序拼接出字符串--！例：a=1&b=2&c=3& 返回字符串加密信息
     *
     * @param map 源数据
     * @return : java.lang.String
     */
    public static String crypt(Map<String, String> map) {
        String[] keyName = new String[map.size()];
        Set<String> setName = map.keySet();
        int i = 0;

        for (String os : setName) {
            keyName[i] = os;
            i++;
        }
        Arrays.sort(keyName);
        StringBuilder md5Sum = new StringBuilder();
        for (int ii = 0; ii < keyName.length; ii++) {
            if (ii != keyName.length - 1) {
                md5Sum.append(keyName[ii]).append("=").append(map.get(keyName[ii])).append("&");
            } else {
                md5Sum.append(keyName[ii]).append("=").append(map.get(keyName[ii]));
            }
        }
        return DigestUtils.md5Hex(md5Sum.toString());
    }

    /**
     * 传入加密字符串返回 byte数组 用于以后 base64有个方法必须要byte[]
     *
     * @param message 源数据
     * @return : byte[]
     */
    public static byte[] rebyte(String message) {
        return DigestUtils.md5(message);
    }
}
