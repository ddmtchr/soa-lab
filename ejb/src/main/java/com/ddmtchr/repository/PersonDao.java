package com.ddmtchr.repository;

import com.ddmtchr.entity.Person;
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
public class PersonDao {

    @PersistenceContext
    private EntityManager em;

    public Optional<Person> find(Long id) {
        return Optional.ofNullable(em.find(Person.class, id));
    }

    public List<Person> findAll() {
        TypedQuery<Person> query = em.createQuery(
                "SELECT p FROM Person p ORDER BY p.id ASC", Person.class);
        return query.getResultList();
    }

    @Transactional
    public Person save(Person p) {
        if (p.getId() == null) {
            em.persist(p);
            return p;
        }
        return em.merge(p);
    }

    @Transactional
    public List<Person> saveAll(Collection<Person> persons) {
        return persons.stream()
                .map(this::save)
                .toList();
    }

    @Transactional
    public void delete(Person p) {
        em.remove(em.contains(p) ? p : em.merge(p));
    }

    public List<Person> findAllByTeam(Team t) {
        return em.createQuery(
                        "SELECT p FROM Person p WHERE p.team = :team",
                        Person.class
                )
                .setParameter("team", t)
                .getResultList();
    }
}
