package com.ddmtchr.service.dto;

import com.ddmtchr.api.dto.cave.CaveResponseDto;
import com.ddmtchr.api.dto.team.TeamRequestDto;
import com.ddmtchr.api.dto.team.TeamResponseDto;
import com.ddmtchr.api.exception.NotFoundException;
import com.ddmtchr.api.service.TeamDtoService;
import com.ddmtchr.entity.Cave;
import com.ddmtchr.entity.Team;
import com.ddmtchr.mapper.CaveMapper;
import com.ddmtchr.mapper.TeamMapper;
import com.ddmtchr.service.CaveService;
import com.ddmtchr.service.TeamService;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Remote(TeamDtoService.class)
@Local(TeamDtoServiceLocal.class)
public class TeamDtoServiceImpl implements TeamDtoServiceLocal {

    @Inject
    private TeamService teamService;

    @Inject
    private CaveService caveService;

    @Inject
    private TeamMapper teamMapper;

    @Inject
    private CaveMapper caveMapper;

    @Override
    public TeamResponseDto findById(Long id) {
        return teamService.findById(id).map(teamMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Team not found"));
    }

    @Override
    public List<TeamResponseDto> findAll() {
        return teamService.findAll().stream().map(teamMapper::toResponseDto).toList();
    }

    @Transactional
    @Override
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
    @Override
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
    @Override
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
