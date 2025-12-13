package com.ddmtchr.service;

import com.ddmtchr.entity.Person;
import com.ddmtchr.repository.DragonDao;
import com.ddmtchr.repository.PersonDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class PersonService {

    @Inject
    private PersonDao personDao;

    @Inject
    private DragonDao dragonDao;

    public Optional<Person> findById(Long id) {
        return personDao.find(id);
    }

    public List<Person> findAll() {
        return personDao.findAll();
    }

    @Transactional
    public Person save(Person entity) {
        return personDao.save(entity);
    }

    @Transactional
    public void delete(Person person) {
        dragonDao.saveAll(dragonDao.findAllByKiller(person).stream()
                .peek(dragon -> dragon.setKiller(null)).toList());
        personDao.delete(person);
    }

}
