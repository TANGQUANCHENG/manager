package cn.decentchina.manager.system.service;


import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Role;
import cn.decentchina.manager.system.vo.RoleVO;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
public interface RoleService {
    /**
     * 查询全部
     *
     * @return
     */
    List<Role> queryAll();

    /**
     * 查询全部(详细)
     *
     * @param searchText
     * @return
     */
    List<RoleVO> queryAllRole(String searchText);


    /**
     * 新增角色
     * @param role
     * @return
     */
    SimpleMessage addRole(Role role) throws Exception;

    /**
     * 修改角色
     * @param role
     * @return
     */
    SimpleMessage updateRole(Role role) throws Exception;

    /**
     * 删除角色
     * @param id
     * @return
     */
    SimpleMessage deleteRole(Integer id) throws Exception;


    /**
     * 修改角色状态
     * @param id
     * @param available
     * @return
     */
    SimpleMessage updateStatus(Integer id, Boolean available);


}
