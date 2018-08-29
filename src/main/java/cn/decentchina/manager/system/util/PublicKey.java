package cn.decentchina.manager.system.util;

import cn.decentchina.manager.system.config.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐全成
 * 2018/2/27
 */
@Slf4j
public class PublicKey {

    public static HashMap<String,String> getPublicKey(HttpServletRequest httpServletRequest){
        HashMap<String,String> publicKeyMap=new HashMap<>(4);
        try {
            Map<String,Object> keyMap= SecurityUtil.getKeyMap();
            RSAPublicKey publicKey=(RSAPublicKey)keyMap.get(Constants.PUBLIC_KEY);
            publicKeyMap.put(Constants.PUBLIC_KEY, RSAUtil.encryptBASE64(publicKey.getEncoded()));
            String randomStr=(String)keyMap.get(Constants.RANDOM_STR);
            publicKeyMap.put(Constants.RANDOM_STR,randomStr);
            //将密钥和随机字符串绑定到session
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(Constants.PRIVATE_KEY, keyMap.get(Constants.PRIVATE_KEY));
            session.setAttribute(Constants.RANDOM_STR, keyMap.get(Constants.RANDOM_STR));

        } catch (Exception e) {
            log.error("getPublicKey error ,e:{}",e.getMessage());
        }

        return publicKeyMap;
    }

}