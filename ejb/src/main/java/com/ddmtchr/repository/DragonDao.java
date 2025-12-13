package com.ddmtchr.repository;

import com.ddmtchr.api.dto.dragon.DragonType;
import com.ddmtchr.api.dto.dragon.DragonTypeCountDto;
import com.ddmtchr.entity.Dragon;
import com.ddmtchr.entity.Person;
import com.ddmtchr.repository.specification.Specification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DragonDao {

    @PersistenceContext
    private EntityManager em;

    public Optional<Dragon> find(Long id) {
        return Optional.ofNullable(em.find(Dragon.class, id));
    }

    public List<Dragon> findAll(Specification<Dragon> spec, int offset, int limit, String sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Dragon> cq = cb.createQuery(Dragon.class);
        Root<Dragon> root = cq.from(Dragon.class);

        cq.where(spec.toPredicate(root, cq, cb));

        List<Order> orders = buildSortOrders(sort, cb, root);
        if (!orders.isEmpty()) {
            cq.orderBy(orders);
        }

        TypedQuery<Dragon> q = em.createQuery(cq);
        q.setFirstResult(offset);
        q.setMaxResults(limit);

        return q.getResultList();
    }

    public long count(Specification<Dragon> spec) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Dragon> root = cq.from(Dragon.class);

        cq.select(cb.count(root));
        cq.where(spec.toPredicate(root, cq, cb));

        return em.createQuery(cq).getSingleResult();
    }

    @Transactional
    public Dragon save(Dragon d) {
        if (d.getId() == null) {
            em.persist(d);
            return d;
        }
        return em.merge(d);
    }

    @Transactional
    public List<Dragon> saveAll(Collection<Dragon> dragons) {
        return dragons.stream()
                .map(this::save)
                .toList();
    }

    @Transactional
    public void delete(Dragon d) {
        em.remove(em.contains(d) ? d : em.merge(d));
    }

    public Optional<Dragon> findMinByName() {
        return em.createQuery(
                        "SELECT d FROM Dragon d ORDER BY d.name ASC",
                        Dragon.class
                )
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }

    public long count() {
        return em.createQuery("SELECT COUNT(d) FROM Dragon d", Long.class)
                .getSingleResult();
    }

    public List<DragonTypeCountDto> countByType() {
        return em.createQuery(
                "SELECT new com.ddmtchr.api.dto.dragon.DragonTypeCountDto(d.type, COUNT(d)) " +
                "FROM Dragon d GROUP BY d.type",
                DragonTypeCountDto.class
        ).getResultList();
    }

    public long countByTypeGreater(DragonType type) {
        return em.createQuery(
                        "SELECT COUNT(d) FROM Dragon d WHERE d.type > :type",
                        Long.class
                )
                .setParameter("type", type)
                .getSingleResult();
    }

    public List<Dragon> findAllByKiller(Person p) {
        return em.createQuery(
                        "SELECT d FROM Dragon d WHERE d.killer = :person",
                        Dragon.class
                )
                .setParameter("person", p)
                .getResultList();
    }

    private List<Order> buildSortOrders(String sort, CriteriaBuilder cb, Root<Dragon> root) {
        if (sort == null || sort.isBlank() || sort.equals("UNSORTED")) {
            return List.of();
        }

        return Arrays.stream(sort.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(orderStr -> {
                    String[] parts = orderStr.split(",");
                    String field = parts[0].trim();
                    String direction = parts.length > 1 ? parts[1].trim().toLowerCase() : "asc";

                    Path<?> path = resolvePath(root, field);

                    return direction.equals("desc")
                            ? cb.desc(path)
                            : cb.asc(path);
                })
                .toList();
    }

    private Path<?> resolvePath(From<?, ?> root, String fieldPath) {
        if (!fieldPath.contains(".")) {
            return root.get(fieldPath);
        }

        From<?, ?> join = root;
        String[] parts = fieldPath.split("\\.");

        for (int i = 0; i < parts.length - 1; i++) {
            join = join.join(parts[i], JoinType.LEFT);
        }

        return join.get(parts[parts.length - 1]);
    }
}
