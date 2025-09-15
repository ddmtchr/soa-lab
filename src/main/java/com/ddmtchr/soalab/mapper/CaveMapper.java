package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.cave.CaveRequestDto;
import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.ddmtchr.soalab.entity.Cave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CaveMapper {

    CaveResponseDto toResponseDto(Cave entity);

    @Mapping(target = "id", ignore = true)
    Cave toEntity(CaveRequestDto dto);
}
