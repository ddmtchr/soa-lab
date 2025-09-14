package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.dto.dragon.DragonRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonResponseDto;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.mapper.DragonMapper;
import com.ddmtchr.soalab.repository.DragonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DragonService {

    private final DragonRepository dragonRepository;
    private final DragonMapper dragonMapper;

    @Transactional
    public DragonResponseDto createDragon(DragonRequestDto requestDto) {
        Dragon entity = dragonMapper.toEntity(requestDto);
        Dragon saved = dragonRepository.save(entity);
        return dragonMapper.toResponseDto(saved);
    }
}
