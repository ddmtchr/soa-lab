package com.ddmtchr.mapper;

import com.ddmtchr.api.dto.coordinates.CoordinatesRequestDto;
import com.ddmtchr.api.dto.coordinates.CoordinatesResponseDto;
import com.ddmtchr.entity.Coordinates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CoordinatesMapper {

    CoordinatesResponseDto toResponseDto(Coordinates entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "x", source = "x", defaultValue = "0.0F")
    Coordinates toEntity(CoordinatesRequestDto dto);
}
