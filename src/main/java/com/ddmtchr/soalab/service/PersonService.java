package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.entity.Person;
import com.ddmtchr.soalab.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public Person save(Person entity) {
        return personRepository.save(entity);
    }

    @Transactional
    public void delete(Person person) {
        personRepository.delete(person);
    }

}
