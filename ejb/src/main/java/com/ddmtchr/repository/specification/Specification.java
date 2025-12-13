package com.ddmtchr.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@FunctionalInterface
public interface Specification<T> {

    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    static <T> Specification<T> and(Specification<T> left, Specification<T> right) {
        return (root, query, cb) ->
                cb.and(left.toPredicate(root, query, cb),
                        right.toPredicate(root, query, cb));
    }

    static <T> Specification<T> unrestricted() {
        return (root, query, cb) -> cb.conjunction();
    }
}
