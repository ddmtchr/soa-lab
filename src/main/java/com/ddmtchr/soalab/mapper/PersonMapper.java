package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.person.PersonRequestDto;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.ddmtchr.soalab.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TeamMapper.class})
public interface PersonMapper {

    PersonResponseDto toResponseDto(Person entity);

    @Mapping(target = "id", ignore = true)
    Person toEntity(PersonRequestDto dto);
}
