package com.ddmtchr.service.dto;

import com.ddmtchr.api.dto.cave.CaveRequestDto;
import com.ddmtchr.api.dto.cave.CaveResponseDto;
import com.ddmtchr.api.exception.NotFoundException;
import com.ddmtchr.api.service.CaveDtoService;
import com.ddmtchr.entity.Cave;
import com.ddmtchr.mapper.CaveMapper;
import com.ddmtchr.service.CaveService;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Remote(CaveDtoService.class)
@Local(CaveDtoServiceLocal.class)
public class CaveDtoServiceImpl implements CaveDtoServiceLocal {

    @Inject
    private CaveService caveService;

    @Inject
    private CaveMapper caveMapper;

    @Override
    public CaveResponseDto findById(Long id) {
        return caveService.findById(id).map(caveMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Cave not found"));
    }

    @Override
    public List<CaveResponseDto> findAll() {
        return caveService.findAll().stream().map(caveMapper::toResponseDto).toList();
    }

    @Transactional
    @Override
    public CaveResponseDto save(CaveRequestDto dto) {
        return caveMapper.toResponseDto(caveService.save(caveMapper.toEntity(dto)));
    }

    @Transactional
    @Override
    public CaveResponseDto update(Long id, CaveRequestDto dto) {
        Cave cave = caveService.findById(id).orElseThrow(() -> new NotFoundException("Cave not found"));
        caveMapper.updateCave(dto, cave);
        return caveMapper.toResponseDto(caveService.save(cave));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Cave cave = caveService.findById(id).orElseThrow(() -> new NotFoundException("Cave not found"));
        caveService.delete(cave);
    }

}
