package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.dto.dragon.DragonType;
import com.ddmtchr.soalab.dto.dragon.DragonTypeCountDto;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.repository.DragonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DragonService {

    private final DragonRepository dragonRepository;

    public Optional<Dragon> findById(Long id) {
        return dragonRepository.findById(id);
    }

    public Page<Dragon> findAll(Specification<Dragon> spec, Pageable pageable) {
        return dragonRepository.findAll(spec, pageable);
    }

    @Transactional
    public Dragon save(Dragon entity) {
        return dragonRepository.save(entity);
    }

    @Transactional
    public void delete(Dragon dragon) {
        dragonRepository.delete(dragon);
    }

    public Optional<Dragon> findMinByName() {
        return dragonRepository.findFirstByOrderByNameAsc();
    }

    public long count() {
        return dragonRepository.count();
    }

    public List<DragonTypeCountDto> countByType() {
        return dragonRepository.countAllByTypes();
    }

    public long countByTypeGreater(DragonType type) {
        return dragonRepository.countByTypeGreaterThan(type);
    }

}
