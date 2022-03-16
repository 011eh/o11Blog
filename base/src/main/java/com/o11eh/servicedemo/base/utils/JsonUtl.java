package com.o11eh.servicedemo.base.utils;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.o11eh.servicedemo.base.exception.BusinessException;

import java.util.List;

/**
 * @author 011eh
 * @since 2022/02/27 12:50
 */
public class JsonUtl {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String ERROR_MESSAGE = "Json解析错误";

    public static <T> List<T> jsonToList(String json, Class<T> elementClass) {
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementClass);

        if (StrUtil.isNotBlank(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, collectionType);
            } catch (JsonProcessingException e) {
                throw BusinessException.e(e.getMessage());
            }
        }
        return null;
    }

    public static <T> T jsonToObject(String json, Class<T> aClass) {
        try {
            return OBJECT_MAPPER.readValue(json, aClass);
        } catch (JsonProcessingException e) {
            throw BusinessException.e(e.getMessage());
        }
    }

    public static String ObjectToJson(Object o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw BusinessException.e(e.getMessage());
        }
    }
}
