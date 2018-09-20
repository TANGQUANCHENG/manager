package cn.decentchina.manager.demo.entity;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 注：该类为demo演示类，可删除
 *
 * @author 唐全成
 * @date 2018-08-29
 */
@Data
public class Member {
    /**
     * 性别
     */
    public enum GenderEnum {
        /**
         * 男
         */
        MALE("男"),
        /**
         * 女
         */
        FEMALE("女"),
        /**
         * 其他
         */
        OTHER("其他");
        private final String str;

        GenderEnum(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }

        public static Map<String, String> getMap() {
            Map<String, String> map = new HashMap<>(4);
            for (GenderEnum en : GenderEnum.values()) {
                map.put(en.name(), en.getStr());
            }
            return map;
        }
    }

    /**
     * pk
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 性别
     */
    private GenderEnum gender;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 头像
     */
    private String avatar;

}
