package cn.decentchina.manager.system.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 唐全成
 */
public class MapToJson {
    public static String toJson(Map<String, String> map) {
        String jsonFromMap = "";
        if (map == null || map.isEmpty()) {
            return jsonFromMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonFromMap = mapper.writeValueAsString(map);
            return jsonFromMap;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonFromMap;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(String json) {
        Map<String, String> map = new HashMap<>(8);
        if (StringUtils.isBlank(json)) {
            return map;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(json, Map.class);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
