package com.ddmtchr.soalab.service.dto;

import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.ddmtchr.soalab.dto.team.TeamRequestDto;
import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import com.ddmtchr.soalab.entity.Cave;
import com.ddmtchr.soalab.entity.Team;
import com.ddmtchr.soalab.exception.NotFoundException;
import com.ddmtchr.soalab.mapper.CaveMapper;
import com.ddmtchr.soalab.mapper.TeamMapper;
import com.ddmtchr.soalab.service.CaveService;
import com.ddmtchr.soalab.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamDtoService {

    private final TeamService teamService;
    private final CaveService caveService;
    private final TeamMapper teamMapper;
    private final CaveMapper caveMapper;

    public TeamResponseDto findById(Long id) {
        return teamService.findById(id).map(teamMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Team not found"));
    }

    public List<TeamResponseDto> findAll() {
        return teamService.findAll().stream().map(teamMapper::toResponseDto).toList();
    }

    @Transactional
    public TeamResponseDto save(TeamRequestDto dto) {
        CaveResponseDto caveDto = dto.getCave();
        Cave newCave = null;

        if (caveDto != null) {
            newCave = findOrCreateCave(caveDto);
        }

        Team team = teamMapper.toEntity(dto);
        team.setCave(newCave);
        return teamMapper.toResponseDto(teamService.save(team));
    }

    @Transactional
    public TeamResponseDto update(Long id, TeamRequestDto dto) {
        Team team = teamService.findById(id).orElseThrow(() -> new NotFoundException("Team not found"));
        CaveResponseDto caveDto = dto.getCave();
        Cave newCave = null;

        if (caveDto != null) {
            newCave = findOrCreateCave(caveDto);
        }

        teamMapper.updateTeam(dto, team);
        team.setCave(newCave);
        return teamMapper.toResponseDto(teamService.save(team));
    }

    @Transactional
    public void delete(Long id) {
        Team team = teamService.findById(id).orElseThrow(() -> new NotFoundException("Team not found"));
        teamService.delete(team);
    }

    private Cave findOrCreateCave(CaveResponseDto caveDto) {
        return caveService.findById(caveDto.getId())
                .orElseGet(() -> caveService.save(
                        caveMapper.toEntity(
                                caveMapper.responseDtoToRequestDto(caveDto)
                        )
                ));
    }
}
