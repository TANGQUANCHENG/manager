package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.RoleDao;
import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.system.entity.Role;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import cn.decentchina.manager.system.service.RoleService;
import cn.decentchina.manager.system.util.ChangeToPinYin;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleNavRelationDao roleNavRelationDao;
    @Resource
    private AdminService adminService;
    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;

    /**
     * 查询全部角色
     *
     * @return : java.util.List<cn.decentchina.manager.system.entity.Role>
     */
    @Override
    public List<Role> queryAll() {
        return roleDao.queryAll();
    }

    /**
     * 查询全部(详细)
     *
     * @param searchText 查询条件
     * @return : java.util.List<cn.decentchina.manager.system.vo.RoleVO>
     */
    @Override
    public List<RoleVO> queryAllRole(String searchText) {
        return roleDao.queryAllRole(searchText);
    }

    /**
     * 新增角色
     *
     * @param role 角色
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage addRole(Role role) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        if (StringUtils.isBlank(role.getName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "角色名称不能为空");
        }
        role.setAvailable(true);
        String spell = ChangeToPinYin.changeToTonePinYin(role.getName());
        Role role1 = roleDao.queryByCode(spell);
        if (role1 != null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "存在相同编码的角色，请换个名字");
        }
        role.setCode(spell);
        if (roleDao.addRole(role) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage updateRole(Role role) throws Exception {
        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }
        String spell = ChangeToPinYin.changeToTonePinYin(role.getName());
        role.setCode(spell);
        if (roleDao.updateRole(role) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleMessage deleteRole(Integer id) throws Exception {

        AdminVO currentAdmin = adminService.getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        List<Admin> admins = roleDao.hasAdmin(id);
        if (admins != null && !admins.isEmpty()) {
            return new SimpleMessage(ErrorCodeEnum.NO, "该角色下还存在管理员，不允许删除");
        }
        if (roleDao.deleteRole(id) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        roleNavRelationDao.deleteByRole(id);
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 修改角色状态
     *
     * @param id        角色id
     * @param available 是否有效
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     */
    @Override
    public SimpleMessage updateStatus(Integer id, Boolean available) {
        Role role = new Role();
        role.setId(id);
        role.setAvailable(available);
        if (roleDao.updateAvailable(role) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

}
