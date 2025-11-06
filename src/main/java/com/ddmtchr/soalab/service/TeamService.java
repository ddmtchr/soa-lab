package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.entity.Team;
import com.ddmtchr.soalab.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Transactional
    public Team save(Team entity) {
        return teamRepository.save(entity);
    }

    @Transactional
    public void delete(Team team) {
        teamRepository.delete(team);
    }
}
