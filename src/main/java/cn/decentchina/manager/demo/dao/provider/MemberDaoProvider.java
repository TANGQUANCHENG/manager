package cn.decentchina.manager.demo.dao.provider;

import cn.decentchina.manager.demo.dto.MemberQueryDTO;
import org.apache.commons.lang3.StringUtils;

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
        sql.append(" order by gmt_create desc");
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

        if (StringUtils.isNotBlank(dto.getName())) {
            sql.append(" and instr(member_name.#{dto.name})>0 ");
        }
        if (dto.getMinAge() != null) {
            sql.append(" and age>#{dto.minAge} ");
        }
        if (dto.getMaxAge() != null) {
            sql.append(" and age<#{dto.maxAge} ");
        }
        if (dto.getQueryStartTime() != null) {
            sql.append(" and gmt_create>=#{dto.queryStartTime} ");
        }
        if (dto.getQueryEndTime() != null) {
            sql.append(" and gmt_create<=#{dto.queryEndTime} ");
        }
        if (dto.getGender() != null) {
            sql.append(" and gender=#{dto.gender}");
        }
    }
}
