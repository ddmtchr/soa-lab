package com.ddmtchr.api.service;

import com.ddmtchr.api.dto.team.TeamRequestDto;
import com.ddmtchr.api.dto.team.TeamResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TeamDtoService {

    TeamResponseDto findById(Long id);

    List<TeamResponseDto> findAll();

    TeamResponseDto save(TeamRequestDto teamRequestDto);

    TeamResponseDto update(Long id, TeamRequestDto teamRequestDto);

    void delete(Long id);
}
