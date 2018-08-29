package cn.decentchina.manager.system.service;

/**
 * @author 唐全成
 * @date 2018-05-22
 */
public interface FilterChainDefinitionsService {
    /**
     * 当管理员修改资源绑定时，调用此方法修改服务器权限
     */
    void reloadFilterChains();
}
