package cn.decentchina.manager.system.dao.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
public class AdminDaoProvider {

    /**
     * 管理员信息分页查询
     *
     * @param map
     * @return
     */
    public String queryAdminListPage(HashMap<String, Object> map) {
        StringBuffer sql = new StringBuffer(200);
        sql.append(" SELECT " +
                " a.id, " +
                " a.NAME, " +
                " a.role_id as roleId, " +
                " a.phone_no AS phoneNo, " +
                " a.`status`, " +
                " a.is_super_admin as superAdmin, " +
                " a.gmt_create AS createTime, " +
                " r.NAME AS role " +
                "FROM " +
                " tbl_shiro_admin a left join " +
                " tbl_shiro_role r " +
                "on " +
                " a.role_id = r.id where 1=1 ");
        queryCondition(map, sql);
        return sql.toString();
    }

    /**
     * 查询条件封装
     *
     * @param map
     * @param sql
     */
    public void queryCondition(HashMap<String, Object> map, StringBuffer sql) {
        String searchText = (String) map.get("searchText");
        if (StringUtils.isNotBlank(searchText)) {
            sql.append(" and ( instr(a.name,#{searchText})>0 or instr(r.name,#{searchText})>0 or instr(a.phone_no,#{searchText})>0 )");
        }

    }
}
