package cn.decentchina.manager.system.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author 唐全成
 * @date 2018-04-05
 */
public interface PublicKeyService {
    /**
     * 获取公钥
     *
     * @param httpServletRequest
     * @return
     */
    HashMap<String, String> getPublicKey(HttpServletRequest httpServletRequest);
}
