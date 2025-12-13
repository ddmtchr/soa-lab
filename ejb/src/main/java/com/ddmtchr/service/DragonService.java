package com.ddmtchr.service;

import com.ddmtchr.api.dto.dragon.DragonType;
import com.ddmtchr.api.dto.dragon.DragonTypeCountDto;
import com.ddmtchr.entity.Dragon;
import com.ddmtchr.entity.Person;
import com.ddmtchr.repository.DragonDao;
import com.ddmtchr.repository.specification.Specification;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Stateless
public class DragonService {

    @Inject
    private DragonDao dragonDao;

    public Optional<Dragon> findById(Long id) {
        return dragonDao.find(id);
    }

    public List<Dragon> findAll(
            Specification<Dragon> spec,
            int page,
            int size,
            String sort
    ) {
        int offset = page * size;
        return dragonDao.findAll(spec, offset, size, sort);
    }

    public long count(Specification<Dragon> spec) {
        return dragonDao.count(spec);
    }

    @Transactional
    public Dragon save(Dragon entity) {
        return dragonDao.save(entity);
    }

    @Transactional
    public void delete(Dragon d) {
        dragonDao.delete(d);
    }

    public Optional<Dragon> findMinByName() {
        return dragonDao.findMinByName();
    }

    public long count() {
        return dragonDao.count();
    }

    public List<DragonTypeCountDto> countByType() {
        return dragonDao.countByType();
    }

    public long countByTypeGreater(DragonType type) {
        return dragonDao.countByTypeGreater(type);
    }

    public List<Dragon> findAllByKiller(Person p) {
        return dragonDao.findAllByKiller(p);
    }
}
