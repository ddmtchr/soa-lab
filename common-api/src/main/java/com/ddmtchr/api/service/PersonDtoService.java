package com.ddmtchr.api.service;

import com.ddmtchr.api.dto.person.PersonRequestDto;
import com.ddmtchr.api.dto.person.PersonResponseDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface PersonDtoService {

    PersonResponseDto findById(Long id);

    List<PersonResponseDto> findAll();

    PersonResponseDto save(PersonRequestDto personRequestDto);

    PersonResponseDto update(Long id, PersonRequestDto personRequestDto);

    void delete(Long id);
}
