package com.ddmtchr.api.service;

import com.ddmtchr.api.dto.cave.CaveRequestDto;
import com.ddmtchr.api.dto.cave.CaveResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface CaveDtoService {

    CaveResponseDto findById(Long id);

    List<CaveResponseDto> findAll();

    CaveResponseDto save(CaveRequestDto dto);

    CaveResponseDto update(Long id, CaveRequestDto dto);

    void delete(Long id);
}
