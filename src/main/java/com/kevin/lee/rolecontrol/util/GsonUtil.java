/**
 * ======================
 *
 * @author : sunhaiyou
 * @date : 2019/12/11
 * ======================
 * Description:
 * ======================
 * Major changes:
 */
package com.kevin.lee.rolecontrol.util;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author kevinlee_m
 * @email 1006236978@qq.com
 * @date 2/24/23 12:23 PM
 */
public class GsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtil.class);

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new GsonDateSerializer())
            .create();

    /**
     * 对象序列化为json字符串
     * Date类型转为时间戳(long)
     *
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            gson.fromJson(json, classOfT);
        } catch (Exception e) {
            LOGGER.error("json={}", json, e);
        }
        return null;
    }


    // 自定义日期序列化
    private static class GsonDateSerializer implements JsonSerializer<Date> {
        @Override
        public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(date.getTime());
        }
    }
}