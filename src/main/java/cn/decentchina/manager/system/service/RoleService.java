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
     * 查询全部角色
     *
     * @return : java.util.List<cn.decentchina.manager.system.entity.Role>
     */
    List<Role> queryAll();

    /**
     * 查询全部(详细)
     *
     * @param searchText 查询条件
     * @return : java.util.List<cn.decentchina.manager.system.vo.RoleVO>
     */
    List<RoleVO> queryAllRole(String searchText);


    /**
     * 新增角色
     *
     * @param role 角色
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage addRole(Role role) throws Exception;

    /**
     * 修改角色
     *
     * @param role 角色
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage updateRole(Role role) throws Exception;

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage deleteRole(Integer id) throws Exception;

    /**
     * 修改角色状态
     *
     * @param id        角色id
     * @param available 是否有效
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    SimpleMessage updateStatus(Integer id, Boolean available);
}
