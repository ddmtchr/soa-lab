package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.cave.CaveRequestDto;
import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.ddmtchr.soalab.entity.Cave;
import com.ddmtchr.soalab.schema.CaveRequest;
import com.ddmtchr.soalab.schema.CaveResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaveMapper {

    CaveResponseDto toResponseDto(Cave entity);

    CaveResponseDto toResponseDto(CaveResponse response);

    CaveResponse toResponse(CaveResponseDto responseDto);

    CaveRequestDto toRequestDto(CaveRequest request);

    CaveRequestDto responseDtoToRequestDto(CaveResponseDto responseDto);

    @Mapping(target = "id", ignore = true)
    Cave toEntity(CaveRequestDto dto);

    Cave toEntity(CaveResponseDto dto);

    @Mapping(target = "id", ignore = true)
    void updateCave(CaveRequestDto requestDto, @MappingTarget Cave cave);

    void updateCave(CaveResponseDto responseDto, @MappingTarget Cave cave);
}
