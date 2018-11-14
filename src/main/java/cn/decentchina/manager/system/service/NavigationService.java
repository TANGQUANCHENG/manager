package cn.decentchina.manager.system.service;


import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Navigation;
import cn.decentchina.manager.system.vo.NavigationVO;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
public interface NavigationService {
    /**
     * 查询全部导航
     *
     * @return : java.util.List<cn.decentchina.manager.system.vo.NavigationVO>
     */
    List<NavigationVO> queryAll();

    /**
     * 新增导航菜单
     *
     * @param navigation 导航菜单
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage addNavigation(Navigation navigation) throws Exception;

    /**
     * 修改导航菜单
     *
     * @param navigation 导航菜单
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage updateNavigation(Navigation navigation) throws Exception;

    /**
     * 删除导航菜单
     *
     * @param id 导航菜单id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage deleteNavigation(Integer id) throws Exception;

    /**
     * 查询一级菜单
     *
     * @return : java.util.List<cn.decentchina.manager.system.entity.Navigation>
     */
    List<Navigation> queryFirstLevel();

    /**
     * 修改菜单状态
     *
     * @param id     菜单id
     * @param status 菜单状态
     * @return : java.util.List<cn.decentchina.manager.system.entity.Navigation>
     * @throws Exception 异常
     */
    SimpleMessage updateStatus(Integer id, Boolean status) throws Exception;
}
