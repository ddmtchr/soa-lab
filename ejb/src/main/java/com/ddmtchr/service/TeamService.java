package com.ddmtchr.service;

import com.ddmtchr.entity.Team;
import com.ddmtchr.repository.PersonDao;
import com.ddmtchr.repository.TeamDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class TeamService {

    @Inject
    private TeamDao teamDao;

    @Inject
    private PersonDao personDao;

    public Optional<Team> findById(Long id) {
        return teamDao.find(id);
    }

    public List<Team> findAll() {
        return teamDao.findAll();
    }

    @Transactional
    public Team save(Team entity) {
        return teamDao.save(entity);
    }

    @Transactional
    public void delete(Team team) {
        personDao.saveAll(personDao.findAllByTeam(team).stream()
                .peek(person -> person.setTeam(null)).toList());
        teamDao.delete(team);
    }
}
