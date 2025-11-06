package com.ddmtchr.soalab.service.dto;

import com.ddmtchr.soalab.dto.cave.CaveRequestDto;
import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.ddmtchr.soalab.entity.Cave;
import com.ddmtchr.soalab.exception.NotFoundException;
import com.ddmtchr.soalab.mapper.CaveMapper;
import com.ddmtchr.soalab.service.CaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaveDtoService {

    private final CaveService caveService;
    private final CaveMapper caveMapper;

    public CaveResponseDto findById(Long id) {
        return caveService.findById(id).map(caveMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Cave not found"));
    }

    public List<CaveResponseDto> findAll() {
        return caveService.findAll().stream().map(caveMapper::toResponseDto).toList();
    }

    @Transactional
    public CaveResponseDto save(CaveRequestDto dto) {
        return caveMapper.toResponseDto(caveService.save(caveMapper.toEntity(dto)));
    }

    @Transactional
    public CaveResponseDto update(Long id, CaveRequestDto dto) {
        Cave cave = caveService.findById(id).orElseThrow(() -> new NotFoundException("Cave not found"));
        caveMapper.updateCave(dto, cave);
        return caveMapper.toResponseDto(caveService.save(cave));
    }

    @Transactional
    public void delete(Long id) {
        Cave cave = caveService.findById(id).orElseThrow(() -> new NotFoundException("Cave not found"));
        caveService.delete(cave);
    }

}
