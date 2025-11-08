package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.dragon.DragonRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonResponseDto;
import com.ddmtchr.soalab.entity.Dragon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CoordinatesMapper.class, PersonMapper.class})
public interface DragonMapper {

    DragonResponseDto toResponseDto(Dragon entity);

    DragonRequestDto responseDtoToRequestDto(DragonResponseDto responseDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "age", source = "age", defaultValue = "1")
    Dragon toEntity(DragonRequestDto dto);

    Dragon toEntity(DragonResponseDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "age", source = "age", defaultValue = "1")
    void updateDragon(DragonRequestDto requestDto, @MappingTarget Dragon dragon);

    void updateDragon(DragonResponseDto requestDto, @MappingTarget Dragon dragon);
}
