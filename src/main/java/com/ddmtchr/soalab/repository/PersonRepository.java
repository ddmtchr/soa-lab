package com.ddmtchr.soalab.repository;

import com.ddmtchr.soalab.entity.Person;
import com.ddmtchr.soalab.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByOrderByIdAsc();

    List<Person> findAllByTeam(Team team);
}
