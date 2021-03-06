package cn.decentchina.manager.system.service.impl;

import cn.decentchina.manager.system.config.Constants;
import cn.decentchina.manager.system.config.CustomerConfig;
import cn.decentchina.manager.system.dao.AdminDao;
import cn.decentchina.manager.common.dto.SimpleMessage;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.common.enums.ErrorCodeEnum;
import cn.decentchina.manager.system.service.AdminService;
import cn.decentchina.manager.system.util.DesUtil;
import cn.decentchina.manager.system.util.RsaUtil;
import cn.decentchina.manager.system.util.SecurityUtil;
import cn.decentchina.manager.system.util.ValidateUtils;
import cn.decentchina.manager.system.vo.AdminVO;
import cn.decentchina.manager.system.vo.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private static final String DEFAULT_PASSWORD = "Abc123##";

    private static final int MINIMUM_ACCOUNT_LENGTH = 2;

    @Resource
    private AdminDao adminDao;
    @Resource
    private CustomerConfig customerConfig;

    /**
     * 查询管理员分页数据
     *
     * @param page       分页信息
     * @param searchText 查询条件
     * @return : cn.decentchina.manager.system.vo.Page<cn.decentchina.manager.system.vo.AdminVO>
     */
    @Override
    public Page<AdminVO> queryAdminListPage(Page page, String searchText) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<AdminVO> admins = adminDao.queryAdminListPage(searchText);
        return new Page<>(admins);
    }

    /**
     * 修改管理员
     *
     * @param adminUser 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage updateAdmin(Admin adminUser) throws Exception {
        AdminVO currentAdmin = getCurrentAdmin();

        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "只有超级管理员允许修改账户信息");
        }
        if (StringUtils.isBlank(adminUser.getName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "管理员名称不能为空");
        }
        if (adminDao.updateAdmin(adminUser) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 新增管理员
     *
     * @param adminUser 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage addAdmin(Admin adminUser) throws Exception {

        AdminVO currentAdmin = getCurrentAdmin();

        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "只有超级管理员允许新增账户");
        }
        if (StringUtils.isBlank(adminUser.getPhoneNo()) || adminUser.getPhoneNo().length() < MINIMUM_ACCOUNT_LENGTH) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "用户账号格式不正确");
        }
        if (StringUtils.isBlank(adminUser.getName())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "用户名称不能为空");
        }
        Admin a = adminDao.queryByPhoneNo(adminUser.getPhoneNo());
        if (a != null) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "该账号已存在");
        }
        if (StringUtils.isBlank(adminUser.getLoginPwd()) || !ValidateUtils.isLegalPassword(adminUser.getLoginPwd())) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "新密码至少为8位数字、大小写字母、特殊符号组合");
        }

        String salt = SecurityUtil.getRandomStr(8);
        //密码为空则填充默认密码
        if (StringUtils.isBlank(adminUser.getLoginPwd())) {
            String pwd = StringUtils.isBlank(customerConfig.getDefaultPassword())
                    ? DEFAULT_PASSWORD
                    : customerConfig.getDefaultPassword();
            String encryptedPwd = DesUtil.encrypt(pwd, Charset.defaultCharset(), salt);
            adminUser.setLoginPwd(encryptedPwd);
            adminUser.setSalt(salt);
            //密码不为空
        } else {
            String encryptedPwd = DesUtil.encrypt(adminUser.getLoginPwd(), Charset.defaultCharset(), salt);
            adminUser.setLoginPwd(encryptedPwd);
            adminUser.setSalt(salt);
        }
        adminUser.setStatus(true);
        if (adminDao.addAdmin(adminUser) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage deleteAdmin(int id) throws Exception {

        AdminVO currentAdmin = getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }
        Admin admin = adminDao.queryById(id);
        if (admin.getSuperAdmin() != null && admin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "超级管理员不允许被删除");
        }
        if (adminDao.deleteAdmin(id) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage updatePassword(String oldPwd, String newPwd) throws Exception {
        AdminVO currentAdmin = getCurrentAdmin();
        String realPwd = DesUtil.decrypt(currentAdmin.getLoginPwd(), DesUtil.CHARSET, currentAdmin.getSalt());
        if (!StringUtils.equals(realPwd, oldPwd)) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "原密码错误");
        }
        String salt = SecurityUtil.getRandomStr(8);
        String encryptedPwd = DesUtil.encrypt(newPwd, DesUtil.CHARSET, salt);
        currentAdmin.setLoginPwd(encryptedPwd);
        currentAdmin.setSalt(salt);
        adminDao.updatePassword(currentAdmin);
        SecurityUtils.getSubject().logout();
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 重置密码
     *
     * @param id 会员id
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage resetPassword(Integer id) throws Exception {

        AdminVO currentAdmin = getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }
        Admin admin = adminDao.queryById(id);
        if (admin == null) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        if (admin.getSuperAdmin() != null && admin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "无法重置超级管理员的密码");
        }
        String newPassword = StringUtils.isBlank(customerConfig.getDefaultPassword())
                ? DEFAULT_PASSWORD
                : customerConfig.getDefaultPassword();
        String salt = SecurityUtil.getRandomStr(8);
        String encryptedPwd = DesUtil.encrypt(newPassword, DesUtil.CHARSET, salt);
        admin.setLoginPwd(encryptedPwd);
        admin.setSalt(salt);
        adminDao.updatePassword(admin);
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 修改管理员状态
     *
     * @param admin 管理员
     * @return : cn.decentchina.manager.common.dto.SimpleMessage
     * @throws Exception 异常
     */
    @Override
    public SimpleMessage updateStatus(Admin admin) throws Exception {

        AdminVO currentAdmin = getCurrentAdmin();
        if (!currentAdmin.getSuperAdmin()) {
            return new SimpleMessage(ErrorCodeEnum.ERROR, "该操作允许超级管理员执行");
        }

        if (adminDao.updateAdminStatus(admin) < 1) {
            return new SimpleMessage(ErrorCodeEnum.ERROR);
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

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
    @Override
    public SimpleMessage login(String phoneNo, String password,
                               RSAPrivateKey privateKey,
                               String randomStr,
                               HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isBlank(phoneNo)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "用户名不能为空");
        }
        Admin admin = adminDao.queryByPhoneNo(phoneNo);
        //判断用户存在
        if (admin == null) {
            return new SimpleMessage(ErrorCodeEnum.NO, "账号不存在");
        }
        //判断用户状态
        if (!admin.getStatus()) {
            return new SimpleMessage(ErrorCodeEnum.NO, "账号已被停用");
        }
        //密码解密
        password = URLDecoder.decode(password, Constants.CHARSET);
        //RSA解密(真实的密码和随机数)
        String pwdAndRandom = RsaUtil.decrypt(privateKey, password);
        //获取真实密码(des解密)
        String realAndRandom = DesUtil.decrypt(admin.getLoginPwd(), DesUtil.CHARSET, admin.getSalt()) + randomStr;
        if (!StringUtils.equals(pwdAndRandom, realAndRandom)) {
            return new SimpleMessage(ErrorCodeEnum.NO, "密码错误");
        }
        log.info("登录账号[{}],时间[{}],登录ip[{}]", phoneNo, System.currentTimeMillis(), httpServletRequest.getRemoteAddr());
        //登录
        SecurityUtils.getSubject().login(new UsernamePasswordToken(admin.getPhoneNo(), admin.getLoginPwd()));
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 获取当前登录用户
     *
     * @return : cn.decentchina.manager.system.vo.AdminVO
     * @throws Exception 异常
     */
    @Override
    public AdminVO getCurrentAdmin() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            throw new Exception("获取当前登录用户失败");
        }
        String phoneNo = (String) subject.getPrincipal();
        if (StringUtils.isBlank(phoneNo)) {
            throw new Exception("获取当前登录用户失败");
        }
        AdminVO admin = adminDao.queryByPhoneNo(phoneNo);
        if (admin == null) {
            throw new Exception("获取当前登录用户失败");
        }
        if (admin.getSuperAdmin() == null) {
            admin.setSuperAdmin(false);
        }
        return admin;
    }
}
