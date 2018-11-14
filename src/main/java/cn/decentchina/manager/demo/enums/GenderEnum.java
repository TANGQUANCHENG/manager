package cn.decentchina.manager.demo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐全成
 * @date 2018-09-26
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
    public final String str;

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
