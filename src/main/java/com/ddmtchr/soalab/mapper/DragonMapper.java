package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.dragon.DragonRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonResponseDto;
import com.ddmtchr.soalab.entity.Dragon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CoordinatesMapper.class, PersonMapper.class})
public interface DragonMapper {

    DragonResponseDto toResponseDto(Dragon entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    Dragon toEntity(DragonRequestDto dto);
}
