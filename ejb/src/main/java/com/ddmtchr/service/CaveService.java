package com.ddmtchr.service;

import com.ddmtchr.entity.Cave;
import com.ddmtchr.repository.CaveDao;
import com.ddmtchr.repository.TeamDao;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class CaveService {

    @Inject
    private CaveDao caveDao;

    @Inject
    private TeamDao teamDao;

    public Optional<Cave> findById(Long id) {
        return caveDao.find(id);
    }

    public List<Cave> findAll() {
        return caveDao.findAll();
    }

    @Transactional
    public Cave save(Cave entity) {
        return caveDao.save(entity);
    }

    @Transactional
    public void delete(Cave cave) {
        teamDao.saveAll(teamDao.findAllByCave(cave).stream()
                .peek(team -> team.setCave(null)).toList());
        caveDao.delete(cave);
    }
}
