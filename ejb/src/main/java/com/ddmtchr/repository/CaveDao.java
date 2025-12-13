package com.ddmtchr.repository;

import com.ddmtchr.entity.Cave;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CaveDao {

    @PersistenceContext
    private EntityManager em;

    public Optional<Cave> find(Long id) {
        return Optional.ofNullable(em.find(Cave.class, id));
    }

    public List<Cave> findAll() {
        TypedQuery<Cave> query = em.createQuery(
                "SELECT c FROM Cave c ORDER BY c.id ASC", Cave.class);
        return query.getResultList();
    }

    @Transactional
    public Cave save(Cave c) {
        if (c.getId() == null) {
            em.persist(c);
            return c;
        }
        return em.merge(c);
    }

    @Transactional
    public void delete(Cave c) {
        em.remove(em.contains(c) ? c : em.merge(c));
    }
}
