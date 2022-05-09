package com.wowoohr.calculators.common.utils;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    /**
     * 实现格式化的时间字符串转时间对象
     */
    private static final String DATEFORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";


    private static final Gson GSON_DEFAULT = new Gson();

    private static final Gson GSON_DEFAULT_SECOND = new GsonBuilder().setDateFormat(DATEFORMAT_DEFAULT).create();
    /**
     * 使用默认的gson对象进行反序列化
     *
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJsonDefault(String json, TypeToken<T> typeToken) {
        return GSON_DEFAULT.fromJson(json, typeToken.getType());
    }


    /**
     * @param null
     * @date: 2020/11/12 14:32
     * @since:
     * @version: 1.0
     * @description: 默认的
     */
    private static final Gson GSON_MAP_LIST = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>() {
    }.getType(), new MapTypeAdapter())
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            }).create();
    /**
     * json字符串转list或者map
     *
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return GSON_MAP_LIST.fromJson(json, typeToken.getType());
    }

    /**
     * json字符串转bean对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return GSON_DEFAULT_SECOND.fromJson(json, cls);

    }

    /**
     * 对象转json
     *
     * @param obj
     * @param format
     * @return
     */
    public static String toJson(Object obj, boolean format) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        /**
         * 设置默认时间格式
         */
        gsonBuilder.setDateFormat(DATEFORMAT_DEFAULT);

        /**
         * 添加格式化设置
         */
        if (format) {
            gsonBuilder.setPrettyPrinting();
        }

        Gson gson = gsonBuilder.create();

        return gson.toJson(obj);
    }

    public static class MapTypeAdapter extends TypeAdapter<Object> {

        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<Object>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:
                    return in.nextString();

                case NUMBER:
                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }

                    // 判断数字是否为整数值
                    long lngNum = (long) dbNum;
                    if (dbNum == lngNum) {
                        return lngNum;
                    } else {
                        return dbNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化无需实现
        }
    }

}
