package com.ddmtchr.api.validation;

import jakarta.ejb.Remote;

@Remote
public interface EntityFieldValidator {

    /**
     * Проверяет, что путь (вроде "killer.team.cave.name") существует
     * и заканчивается на простой тип, а не на сущность.
     */
    boolean isValidFieldPath(Class<?> entityClass, String fieldPath);

    Class<?> getFieldType(Class<?> entityClass, String fieldPath);
}