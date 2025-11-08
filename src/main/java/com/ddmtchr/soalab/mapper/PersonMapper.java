package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.person.PersonRequestDto;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.ddmtchr.soalab.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TeamMapper.class})
public interface PersonMapper {

    PersonResponseDto toResponseDto(Person entity);

    PersonRequestDto responseDtoToRequestDto(PersonResponseDto responseDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "height", source = "height", defaultValue = "1L")
    @Mapping(target = "weight", source = "weight", defaultValue = "1.0")
    Person toEntity(PersonRequestDto dto);

    Person toEntity(PersonResponseDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "height", source = "height", defaultValue = "1L")
    @Mapping(target = "weight", source = "weight", defaultValue = "1.0")
    void updatePerson(PersonRequestDto requestDto, @MappingTarget Person person);

    void updatePerson(PersonResponseDto responseDto, @MappingTarget Person person);
}
