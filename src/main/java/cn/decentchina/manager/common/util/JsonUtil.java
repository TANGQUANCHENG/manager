package cn.decentchina.manager.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;


/**
 * json转换
 *
 * @author zhangchao
 * @date 2018/5/22 10:34
 */
public class JsonUtil {

    public static <T> String Java2Json(T t) {
        return JSON.toJSONString(t, SerializerFeature.WriteMapNullValue);
    }

    public static <T> String JavaList2Json(List<T> tList) {
        return JSON.toJSONString(tList, SerializerFeature.WriteMapNullValue);
    }

    public static <T> T Json2Java(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> Json2JavaList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}

