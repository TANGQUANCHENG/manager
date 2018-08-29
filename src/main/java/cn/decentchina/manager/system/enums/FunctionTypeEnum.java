package cn.decentchina.manager.system.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐全成
 * @date 2018-05-18
 */
public enum FunctionTypeEnum {
    /**
     * 菜单
     */
    MENU("菜单"),
    /**
     * 连接
     */
    LINK("连接");

    private final String str;

    FunctionTypeEnum(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public static Map<String, String> getStatusMap() {
        Map<String, String> map = new HashMap<>();
        for (FunctionTypeEnum typeEnum : FunctionTypeEnum.values()) {
            map.put(typeEnum.name(), typeEnum.getStr());
        }
        return map;
    }
}
