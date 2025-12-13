package com.ddmtchr.mapper;

import com.ddmtchr.api.dto.cave.CaveRequestDto;
import com.ddmtchr.api.dto.cave.CaveResponseDto;
import com.ddmtchr.entity.Cave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CaveMapper {

    CaveResponseDto toResponseDto(Cave entity);

    CaveRequestDto responseDtoToRequestDto(CaveResponseDto responseDto);

    @Mapping(target = "id", ignore = true)
    Cave toEntity(CaveRequestDto dto);

    Cave toEntity(CaveResponseDto dto);

    @Mapping(target = "id", ignore = true)
    void updateCave(CaveRequestDto requestDto, @MappingTarget Cave cave);

    void updateCave(CaveResponseDto responseDto, @MappingTarget Cave cave);
}
