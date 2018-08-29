package cn.decentchina.manager.system.service;


import cn.decentchina.manager.system.vo.NavigationVO;
import cn.decentchina.manager.system.vo.ShiroChainVO;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-22
 */
public interface ShiroChainService {
    /**
     * 查询权限树
     * @return
     */
    List<ShiroChainVO> queryShiroChain();

    /**
     * 跟据角色查询导航
     * @param roleId
     * @return
     */
    List<NavigationVO> queryByRole(Integer roleId);

    /**
     * 查询全部
     * @return
     */
    List<NavigationVO> queryAll();
}
