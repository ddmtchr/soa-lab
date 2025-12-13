package com.ddmtchr.api.service;

import com.ddmtchr.api.dto.api.filter.FilterRequestDto;
import com.ddmtchr.api.dto.dragon.*;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface DragonDtoService {

    DragonResponseDto findById(Long id);

    PagedDragonListDto search(FilterRequestDto filter, int page, int size, String sort);

    DragonResponseDto save(DragonRequestDto dto);

    DragonResponseDto update(Long id, DragonRequestDto dto);

    void delete(Long id);

    DragonResponseDto findMinByName();

    long count();

    List<DragonTypeCountDto> countByType();

    long countByTypeGreater(DragonType type);
}
