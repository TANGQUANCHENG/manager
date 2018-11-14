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
     * 查询管理员分页数据
     *
     * @param page       分页信息
     * @param searchText 查询条件
     * @return : cn.decentchina.manager.system.vo.Page<cn.decentchina.manager.system.vo.AdminVO>
     */
    Page<AdminVO> queryAdminListPage(Page page, String searchText);


    /**
     * 修改管理员
     *
     * @param adminUser 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage updateAdmin(Admin adminUser) throws Exception;

    /**
     * 新增管理员
     *
     * @param adminUser 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage addAdmin(Admin adminUser) throws Exception;

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage deleteAdmin(int id) throws Exception;


    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage updatePassword(String oldPwd, String newPwd) throws Exception;

    /**
     * 重置密码
     *
     * @param id 会员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage resetPassword(Integer id) throws Exception;

    /**
     * 修改管理员状态
     *
     * @param admin 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage updateStatus(Admin admin) throws Exception;

    /**
     * 管理员登录
     *
     * @param phoneNo            手机号
     * @param password           密码
     * @param privateKey         私钥
     * @param randomStr          随机数
     * @param httpServletRequest 请求
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    SimpleMessage login(String phoneNo, String password,
                        RSAPrivateKey privateKey,
                        String randomStr,
                        HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 获取当前登录用户
     *
     * @return : cn.decentchina.manager.system.vo.AdminVO
     * @throws Exception 异常
     */
    AdminVO getCurrentAdmin() throws Exception;
}
