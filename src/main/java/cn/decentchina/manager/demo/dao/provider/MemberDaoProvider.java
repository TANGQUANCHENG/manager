package cn.decentchina.manager.demo.dao.provider;

import cn.decentchina.manager.demo.dto.MemberQueryDTO;

import java.util.HashMap;

/**
 * @author 唐全成
 * @date 2018-08-29
 */
public class MemberDaoProvider {
    /**
     * 信息分页查询
     *
     * @param map
     * @return
     */
    public String queryMemberList(HashMap<String, Object> map) {
        StringBuffer sql = new StringBuffer(200);
        sql.append("  select id," +
                " member_name as name," +
                " age," +
                " gender," +
                " gmt_create as createTime," +
                " gmt_modified as modifyTime" +
                " from tbl_member " +
                " where 1=1 ");
        queryCondition(map, sql);
        sql.append(" order by o.gmt_create desc");
        return sql.toString();
    }

    /**
     * 查询条件封装
     *
     * @param map
     * @param sql
     */
    private void queryCondition(HashMap<String, Object> map, StringBuffer sql) {
        MemberQueryDTO dto = (MemberQueryDTO) map.get("dto");
    }
}
