package cn.decentchina.manager.system.service;


import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.vo.TreeVO;

/**
 * @author 唐全成
 * @date 2018-05-19
 */
public interface BindService {
    /**
     * 绑定
     * @param navs
     * @param roles
     * @return
     */
    SimpleMessage batchBind(Integer[] navs, Integer[] roles) throws Exception;

    /**
     * 解除
     * @param relationId
     * @return
     */
    SimpleMessage relieveBind(Integer[] relationId) throws Exception;

    /**
     * 根据角色查询菜单
     * @param roleId
     * @return
     */
    TreeVO queryByRole(Integer roleId);


}
