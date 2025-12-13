package com.ddmtchr.mapper;

import com.ddmtchr.api.dto.team.TeamRequestDto;
import com.ddmtchr.api.dto.team.TeamResponseDto;
import com.ddmtchr.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI,
        uses = {CaveMapper.class})
public interface TeamMapper {

    TeamResponseDto toResponseDto(Team entity);

    TeamRequestDto responseDtoToRequestDto(TeamResponseDto responseDto);

    @Mapping(target = "id", ignore = true)
    Team toEntity(TeamRequestDto dto);

    Team toEntity(TeamResponseDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cave", ignore = true)
    void updateTeam(TeamRequestDto requestDto, @MappingTarget Team team);

    void updateTeam(TeamResponseDto responseDto, @MappingTarget Team team);
}
