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
     * 查询全部
     *
     * @return
     */
    List<NavigationVO> queryAll();

    /**
     * 新增功能
     * @param navigation
     * @throws Exception
     * @return
     */
    SimpleMessage addNavigation(Navigation navigation) throws Exception;

    /**
     * 修改功能
     * @param navigation
     * @throws Exception
     * @return
     */
    SimpleMessage updateNavigation(Navigation navigation) throws Exception;

    /**
     * 删除功能
     * @param id
     * @throws Exception
     * @return
     */
    SimpleMessage deleteNavigation(Integer id) throws Exception;

    /**
     * 查询一级菜单
     * @return
     */
    List<Navigation> queryFirstLevel();

    /**
     * 修改菜单状态
     * @param id
     * @param status
     * @throws Exception
     * @return
     */
    SimpleMessage updateStatus(Integer id, Boolean status) throws Exception;
}
