package com.ddmtchr.service.dto;

import com.ddmtchr.api.dto.person.PersonRequestDto;
import com.ddmtchr.api.dto.person.PersonResponseDto;
import com.ddmtchr.api.dto.team.TeamResponseDto;
import com.ddmtchr.api.exception.NotFoundException;
import com.ddmtchr.api.service.PersonDtoService;
import com.ddmtchr.entity.Person;
import com.ddmtchr.entity.Team;
import com.ddmtchr.mapper.PersonMapper;
import com.ddmtchr.mapper.TeamMapper;
import com.ddmtchr.service.PersonService;
import com.ddmtchr.service.TeamService;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Remote(PersonDtoService.class)
@Local(PersonDtoServiceLocal.class)
public class PersonDtoServiceImpl implements PersonDtoServiceLocal {

    @Inject
    private PersonService personService;

    @Inject
    private TeamDtoServiceLocal teamDtoService;

    @Inject
    private TeamService teamService;

    @Inject
    private PersonMapper personMapper;

    @Inject
    private TeamMapper teamMapper;

    @Override
    public PersonResponseDto findById(Long id) {
        return personService.findById(id).map(personMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    @Override
    public List<PersonResponseDto> findAll() {
        return personService.findAll().stream().map(personMapper::toResponseDto).toList();
    }

    @Transactional
    @Override
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
    @Override
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
    @Override
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
