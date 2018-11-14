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
     *
     * @param navs  资源id
     * @param roles 角色id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage batchBind(Integer[] navs, Integer[] roles) throws Exception;

    /**
     * 解除
     *
     * @param relationId 关联id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage relieveBind(Integer[] relationId) throws Exception;

    /**
     * 根据角色查询菜单
     *
     * @param roleId 角色id
     * @return : cn.decentchina.manager.system.vo.TreeVO
     */
    TreeVO queryByRole(Integer roleId);
}
