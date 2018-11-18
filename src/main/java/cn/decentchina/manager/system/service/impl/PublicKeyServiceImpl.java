package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.service.PublicKeyService;
import cn.decentchina.manager.system.util.PublicKey;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author 唐全成
 * @date 2018-04-05
 */
@Service
public class PublicKeyServiceImpl implements PublicKeyService {
    /**
     * 获取公钥
     *
     * @param httpServletRequest 请求
     * @return : java.util.HashMap<java.lang.String,java.lang.String>
     */
    @Override
    public HashMap<String, String> getPublicKey(HttpServletRequest httpServletRequest) {
        return PublicKey.getPublicKey(httpServletRequest);
    }
}
