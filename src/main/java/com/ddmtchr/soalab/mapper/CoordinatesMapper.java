package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.coordinates.CoordinatesRequestDto;
import com.ddmtchr.soalab.dto.coordinates.CoordinatesResponseDto;
import com.ddmtchr.soalab.entity.Coordinates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoordinatesMapper {

    CoordinatesResponseDto toResponseDto(Coordinates entity);

    @Mapping(target = "id", ignore = true)
    Coordinates toEntity(CoordinatesRequestDto dto);
}
