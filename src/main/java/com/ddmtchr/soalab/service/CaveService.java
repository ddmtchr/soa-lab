package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.entity.Cave;
import com.ddmtchr.soalab.repository.CaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaveService {

    private final CaveRepository caveRepository;

    public Optional<Cave> findById(Long id) {
        return caveRepository.findById(id);
    }

    public List<Cave> findAll() {
        return caveRepository.findAll();
    }

    @Transactional
    public Cave save(Cave entity) {
        return caveRepository.save(entity);
    }

    @Transactional
    public void delete(Cave cave) {
        caveRepository.delete(cave);
    }

}
