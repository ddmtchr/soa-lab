package com.ddmtchr.soalab.repository.specification;

import com.ddmtchr.soalab.dto.api.FilterOperation;
import com.ddmtchr.soalab.dto.api.filter.FilterCriteria;
import com.ddmtchr.soalab.dto.api.filter.FilterRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonType;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.exception.FilterValidationException;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class DragonSpecificationFactory {

    public static Specification<Dragon> byFilters(FilterRequestDto filterDto) {
        if (filterDto == null || filterDto.getFilters() == null || filterDto.getFilters().isEmpty()) {
            return Specification.unrestricted();
        }

        return filterDto.getFilters().stream()
                .map(DragonSpecificationFactory::mapToSpecification)
                .reduce(Specification::and)
                .orElse(null);
    }

    private static Specification<Dragon> mapToSpecification(FilterCriteria criteria) {
        return (root, query, cb) -> {
            Path<?> path = getPath(root, criteria.getField());
            FilterOperation op = criteria.getOp();
            String value = criteria.getValue();
            Object converted = convertValue(path, value);

            return switch (op) {
                case EQ -> cb.equal(path, converted);
                case NE -> cb.notEqual(path, converted);
                case LIKE -> cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%");
                case GT -> buildComparablePredicate(cb, path, converted, CompareOp.GT);
                case LT -> buildComparablePredicate(cb, path, converted, CompareOp.LT);
                case GE -> buildComparablePredicate(cb, path, converted, CompareOp.GE);
                case LE -> buildComparablePredicate(cb, path, converted, CompareOp.LE);
                default -> throw new IllegalArgumentException("Unknown operation: " + op);
            };
        };
    }

    private static Path<?> getPath(From<?, ?> root, String fieldPath) {
        if (!fieldPath.contains(".")) {
            return root.get(fieldPath);
        }

        String[] parts = fieldPath.split("\\.");
        From<?, ?> join = root;
        for (int i = 0; i < parts.length - 1; i++) {
            join = join.join(parts[i], JoinType.LEFT);
        }
        return join.get(parts[parts.length - 1]);
    }

    private static Object convertValue(Path<?> path, String value) {
        Class<?> type = path.getJavaType();
        try {
            if (type == Integer.class || type == int.class) return Integer.valueOf(value);
            if (type == Long.class || type == long.class) return Long.valueOf(value);
            if (type == Double.class || type == double.class) return Double.valueOf(value);
            if (type == Float.class || type == float.class) return Float.valueOf(value);
            if (type == Boolean.class || type == boolean.class) return Boolean.valueOf(value);
            if (type == ZonedDateTime.class) return ZonedDateTime.parse(value);
            if (type == DragonType.class) return DragonType.valueOf(value);
            return value;
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new FilterValidationException("Failed to convert param with value '" + value + "' to type: '" + type.getSimpleName() + "'", e);
        }
    }

    private static <T extends Comparable<? super T>> Predicate buildComparablePredicate(
            CriteriaBuilder cb, Path<?> path, Object value, CompareOp op) {
        if (!(value instanceof Comparable<?> comparableValue)) {
            throw new IllegalArgumentException("Field " + path + " is not comparable");
        }

        Expression<? extends T> expr = (Expression<? extends T>) path;
        T val = (T) comparableValue;

        return switch (op) {
            case GT -> cb.greaterThan(expr, val);
            case LT -> cb.lessThan(expr, val);
            case GE -> cb.greaterThanOrEqualTo(expr, val);
            case LE -> cb.lessThanOrEqualTo(expr, val);
        };
    }

    private enum CompareOp {
        GT, LT, GE, LE
    }
}
