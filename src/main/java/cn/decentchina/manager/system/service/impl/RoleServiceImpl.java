package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.dao.RoleDao;
import cn.decentchina.manager.system.dao.RoleNavRelationDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.system.entity.Role;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.FilterChainDefinitionsService;
import cn.decentchina.manager.system.service.RoleService;
import cn.decentchina.manager.system.util.ChangeToPinYin;
import cn.decentchina.manager.system.vo.RoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleNavRelationDao roleNavRelationDao;

    @Autowired
    private FilterChainDefinitionsService filterChainDefinitionsService;
    @Override
    public List<Role> queryAll() {
        return roleDao.queryAll();
    }

    @Override
    public List<RoleVO> queryAllRole(String searchText) {
        return roleDao.queryAllRole(searchText);
    }

    @Override
    public SimpleMessage addRole(Role role) {
        if(StringUtils.isBlank(role.getName())){
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS,"角色名称不能为空");
        }
        role.setAvailable(true);
        String spell = ChangeToPinYin.changeToTonePinYin(role.getName());
        Role role1 = roleDao.queryByCode(spell);
        if(role1!=null){
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS,"存在相同编码的角色，请换个名字");
        }
        role.setCode(spell);
        if(roleDao.addRole(role)<1){
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public SimpleMessage updateRole(Role role) {

        String spell = ChangeToPinYin.changeToTonePinYin(role.getName());
        role.setCode(spell);
        if(roleDao.updateRole(role)<1){
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SimpleMessage deleteRole(Integer id) {

        List<Admin> admins = roleDao.hasAdmin(id);
        if(admins!=null&&!admins.isEmpty()){
            return new SimpleMessage(ErrorCodeEnum.NO,"该角色下还存在管理员，不允许删除");
        }
        if(roleDao.deleteRole(id)<1){
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        roleNavRelationDao.deleteByRole(id);
        filterChainDefinitionsService.reloadFilterChains();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @Override
    public SimpleMessage updateStatus(Integer id, Boolean available) {
        Role role=new Role();
        role.setId(id);
        role.setAvailable(available);
        if(roleDao.updateAvailable(role)<1){
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

}
