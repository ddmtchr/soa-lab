package com.ddmtchr.repository;

import com.ddmtchr.entity.Cave;
import com.ddmtchr.entity.Team;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TeamDao {
    
    @PersistenceContext
    private EntityManager em;

    public Optional<Team> find(Long id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    public List<Team> findAll() {
        TypedQuery<Team> query = em.createQuery(
                "SELECT t FROM Team t ORDER BY t.id ASC", Team.class);
        return query.getResultList();
    }

    @Transactional
    public Team save(Team t) {
        if (t.getId() == null) {
            em.persist(t);
            return t;
        }
        return em.merge(t);
    }

    @Transactional
    public List<Team> saveAll(Collection<Team> teams) {
        return teams.stream()
                .map(this::save)
                .toList();
    }

    @Transactional
    public void delete(Team t) {
        em.remove(em.contains(t) ? t : em.merge(t));
    }

    public List<Team> findAllByCave(Cave c) {
        return em.createQuery(
                        "SELECT t FROM Team t WHERE t.cave = :cave",
                        Team.class
                )
                .setParameter("cave", c)
                .getResultList();
    }
}
