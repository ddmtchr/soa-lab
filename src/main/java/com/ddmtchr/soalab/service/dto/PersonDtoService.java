package com.ddmtchr.soalab.service.dto;

import com.ddmtchr.soalab.dto.person.PersonRequestDto;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import com.ddmtchr.soalab.entity.Person;
import com.ddmtchr.soalab.entity.Team;
import com.ddmtchr.soalab.exception.NotFoundException;
import com.ddmtchr.soalab.mapper.PersonMapper;
import com.ddmtchr.soalab.mapper.TeamMapper;
import com.ddmtchr.soalab.service.PersonService;
import com.ddmtchr.soalab.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonDtoService {

    private final PersonService personService;
    private final TeamDtoService teamDtoService;
    private final TeamService teamService;
    private final PersonMapper personMapper;
    private final TeamMapper teamMapper;

    public PersonResponseDto findById(Long id) {
        return personService.findById(id).map(personMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    public List<PersonResponseDto> findAll() {
        return personService.findAll().stream().map(personMapper::toResponseDto).toList();
    }

    @Transactional
    public PersonResponseDto save(PersonRequestDto dto) {
        TeamResponseDto teamDto = dto.getTeam();
        Team newTeam = null;

        if (teamDto != null) {
            newTeam = findOrCreateTeam(teamDto);
        }

        Person person = personMapper.toEntity(dto);
        person.setTeam(newTeam);
        return personMapper.toResponseDto(personService.save(person));
    }

    @Transactional
    public PersonResponseDto update(Long id, PersonRequestDto dto) {
        Person person = personService.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
        TeamResponseDto teamDto = dto.getTeam();
        Team newTeam = null;

        if (teamDto != null) {
            newTeam = findOrCreateTeam(teamDto);
        }

        personMapper.updatePerson(dto, person);
        person.setTeam(newTeam);
        return personMapper.toResponseDto(personService.save(person));
    }

    @Transactional
    public void delete(Long id) {
        Person person = personService.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
        personService.delete(person);
    }

    private Team findOrCreateTeam(TeamResponseDto teamDto) {
        return teamService.findById(teamDto.getId())
                .orElseGet(() -> {
                    TeamResponseDto saved = teamDtoService.save(
                            teamMapper.responseDtoToRequestDto(teamDto)
                    );
                    return teamMapper.toEntity(saved);
                });
    }
}
