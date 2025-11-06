package com.ddmtchr.soalab.validation;

import com.ddmtchr.soalab.util.ReflectionUtil;
import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Set;

@Component
public class EntityFieldValidator {

    private static final Set<Class<?>> SIMPLE_TYPES = Set.of(
            String.class, Integer.class, int.class, Long.class, long.class,
            Double.class, double.class, Float.class, float.class,
            Boolean.class, boolean.class, LocalDate.class, ZonedDateTime.class, Enum.class
    );

    /**
     * Проверяет, что путь (вроде "killer.team.cave.name") существует
     * и заканчивается на простой тип, а не на сущность.
     */
    public boolean isValidFieldPath(Class<?> entityClass, String fieldPath) {
        try {
            String[] parts = fieldPath.split("\\.");
            Class<?> current = entityClass;

            for (int i = 0; i < parts.length; i++) {
                Field field = getField(current, parts[i]);
                if (field == null) {
                    return false;
                }

                Class<?> type = field.getType();

                if (i == parts.length - 1) {
                    return isSimpleType(type);
                }

                if (!isEntity(type)) {
                    return false;
                }

                current = type;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Field getField(Class<?> clazz, String fieldName) {
        for (Field field : ReflectionUtil.getAllFieldsFromCache(clazz)) {
            if (field.getName().equals(fieldName)) return field;
        }
        return null;
    }

    private boolean isSimpleType(Class<?> type) {
        return SIMPLE_TYPES.contains(type)
               || type.isEnum()
               || type.isPrimitive()
               || Number.class.isAssignableFrom(type);
    }

    private boolean isEntity(Class<?> type) {
        return type.isAnnotationPresent(Entity.class);
    }
}
