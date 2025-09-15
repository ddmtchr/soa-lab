package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.team.TeamRequestDto;
import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import com.ddmtchr.soalab.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CaveMapper.class})
public interface TeamMapper {

    TeamResponseDto toResponseDto(Team entity);

    @Mapping(target = "id", ignore = true)
    Team toEntity(TeamRequestDto dto);
}
