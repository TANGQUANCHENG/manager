package cn.decentchina.manager.system.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author 唐全成
 */
public class MapToJson {
    public static String toJson(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonfromMap = mapper.writeValueAsString(map);
            return jsonfromMap;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> toMap(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> map = mapper.readValue(json, Map.class);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
