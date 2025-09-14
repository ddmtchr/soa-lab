package com.ddmtchr.soalab.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

    private ReflectionUtil() {
    }

    private static final Map<Class<?>, List<Field>> DECLARED_FIELDS_CACHE = new HashMap<>();

    public static List<Field> getAllFieldsFromCache(Class<?> clazz) {
        return DECLARED_FIELDS_CACHE.computeIfAbsent(clazz, FieldUtils::getAllFieldsList);
    }

}
