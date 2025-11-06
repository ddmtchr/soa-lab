package com.ddmtchr.soalab.mapper;

import com.ddmtchr.soalab.dto.team.TeamRequestDto;
import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import com.ddmtchr.soalab.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
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
