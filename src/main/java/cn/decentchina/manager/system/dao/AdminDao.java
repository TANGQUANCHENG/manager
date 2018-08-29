package cn.decentchina.manager.system.dao;

import cn.decentchina.manager.system.dao.provider.AdminDaoProvider;
import cn.decentchina.manager.system.entity.Admin;
import cn.decentchina.manager.system.vo.AdminVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@Mapper
@Repository
public interface AdminDao {


    /**
     * 新增管理员
     *
     * @param admin
     * @return
     */
    @Insert("INSERT INTO tbl_shiro_admin (" +
            " name, " +
            " role_id," +
            " phone_no," +
            " login_pwd, " +
            " salt, " +
            " status, " +
            " gmt_create " +
            ") VALUES " +
            "(" +
            "#{admin.name}," +
            "#{admin.roleId}," +
            "#{admin.phoneNo}," +
            "#{admin.loginPwd}, " +
            "#{admin.salt}, " +
            "#{admin.status}," +
            "now()" +
            ")")
    int addAdmin(@Param("admin") Admin admin);


    /**
     * 信息分页查询
     *
     * @param searchText 搜索条件
     * @return
     */
    @SelectProvider(type = AdminDaoProvider.class, method = "queryAdminListPage")
    List<AdminVO> queryAdminListPage(@Param("searchText") String searchText);

    /**
     * 修改管理员
     *
     * @param admin
     * @return
     */
    @Update(" UPDATE tbl_shiro_admin SET" +
            " name=#{admin.name}," +
            " role_id=#{admin.roleId}" +
            " WHERE ID=#{admin.id}")
    int updateAdmin(@Param("admin") Admin admin);

    /**
     * 修改管理员状态
     *
     * @param admin
     * @return
     */
    @Update(" UPDATE tbl_shiro_admin SET status=#{admin.status} WHERE id=#{admin.id}")
    int updateAdminStatus(@Param("admin") Admin admin);


    /**
     * 根据管理员id查询管理员信息
     *
     * @param id
     * @return
     */
    @Select("select " +
            "id," +
            "name, " +
            "role_id as roleId," +
            "phone_no as phoneNo, " +
            "login_pwd as loginPwd, " +
            "salt as salt, " +
            "is_super_admin as superAdmin, " +
            "status " +
            "from tbl_shiro_admin " +
            "where id=#{id} ")
    Admin queryById(@Param("id") Integer id);

    /**
     * 根据管理员手机号码查询管理员信息
     *
     * @param phoneNo
     * @return
     */
    @Select("select " +
            "a.id," +
            "a.name, " +
            "a.role_id as roleId," +
            "a.phone_no as phoneNo, " +
            "a.login_pwd as loginPwd, " +
            "a.salt as salt, " +
            "a.is_super_admin as superAdmin, " +
            "a.status, " +
            "r.name as role, "+
            "r.code as roleCode "+
            "from tbl_shiro_admin a left join tbl_shiro_role r on a.role_id=r.id  " +
            "where a.phone_no=#{phoneNo} ")
    AdminVO queryByPhoneNo(@Param("phoneNo") String phoneNo);

    /**
     * 删除管理员
     *
     * @param id
     * @return
     */
    @Delete("delete from tbl_shiro_admin where id=#{id}")
    int deleteAdmin(@Param("id") int id);

    /**
     * 修改密码
     * @param admin 管理员实体
     * @return
     */
    @Update("update tbl_shiro_admin set login_pwd=#{admin.loginPwd},salt=#{admin.salt} where phone_no=#{admin.phoneNo} ")
    int updatePassword(@Param("admin") Admin admin);
    
}
