package cn.decentchina.manager.system.service;

import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.Page;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPrivateKey;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
public interface AdminService {

    /**
     * 管理员管理列表
     *
     * @param page       分页实体
     * @param searchText 查询条件
     * @return
     */
    Page<AdminVO> queryAdminListPage(Page page, String searchText);


    /**
     * 修改管理员信息
     *
     * @param adminUser
     * @return
     */
    SimpleMessage updateAdmin(Admin adminUser) throws Exception;

    /**
     * 新增管理员信息
     *
     * @param adminUser
     * @return
     */
    SimpleMessage addAdmin(Admin adminUser) throws Exception;

    /**
     * 删除管理员信息
     *
     * @param id
     * @return
     */
    SimpleMessage deleteAdmin(int id) throws Exception;


    /**
     * 修改密码
     *
     * @param oldPwd     旧密码
     * @param newPwd     新密码
     * @return
     * @throws Exception
     */
    SimpleMessage updatePassword(String oldPwd, String newPwd) throws Exception;

    /**
     * 重置密码
     *
     * @param id 管理员id
     * @return
     */
    SimpleMessage resetPassword(Integer id) throws Exception;


    /**
     * 修改状态
     *
     * @param admin
     * @return
     */
    SimpleMessage updateStatus(Admin admin) throws Exception;

    /**
     * 管理员登录
     *
     * @param phoneNo
     * @param password
     * @param privateKey
     * @param randomStr
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    SimpleMessage login(String phoneNo, String password,
                        RSAPrivateKey privateKey,
                        String randomStr,
                        HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 获取当前登录用户
     * @exception
     * @return
     */
    AdminVO getCurrentAdmin() throws Exception;
}
