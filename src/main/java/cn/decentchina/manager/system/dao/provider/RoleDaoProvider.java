package cn.decentchina.manager.system.dao.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
@SuppressWarnings("unused")
public class RoleDaoProvider {
    /**
     * 管理员信息分页查询
     *
     * @param map 参数
     * @return : java.lang.String
     */
    public String queryAllRole(HashMap<String, Object> map) {
        StringBuffer sql = new StringBuffer(200);
        sql.append("SELECT " +
                " r.id, " +
                " r.NAME, " +
                " r.CODE, " +
                " r.is_available AS available, " +
                " r.gmt_create AS createTime, " +
                " r.COMMENT, " +
                " a.name as creatorName " +
                "FROM " +
                " tbl_shiro_role r left join tbl_shiro_admin a on r.creator_id = a.id " +
                "WHERE " +
                " 1=1 ");
        queryCondition(map, sql);
        return sql.toString();
    }

    /**
     * 查询条件封装
     *
     * @param map 参数
     * @param sql sql
     */
    private void queryCondition(HashMap<String, Object> map, StringBuffer sql) {
        String searchText = (String) map.get("searchText");
        if (StringUtils.isNotBlank(searchText)) {
            sql.append(" and ( instr(r.name,#{searchText}) or instr(r.code,#{searchText}) or instr(r.COMMENT,#{searchText}) )");
        }

    }
}
