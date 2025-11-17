package com.ddmtchr.soalab.service.dto;

import com.ddmtchr.soalab.dto.api.FilterOperation;
import com.ddmtchr.soalab.dto.api.filter.FilterCriteria;
import com.ddmtchr.soalab.dto.api.filter.FilterRequestDto;
import com.ddmtchr.soalab.dto.dragon.*;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.entity.Person;
import com.ddmtchr.soalab.exception.FilterValidationException;
import com.ddmtchr.soalab.exception.NotFoundException;
import com.ddmtchr.soalab.exception.PageableValidationException;
import com.ddmtchr.soalab.mapper.DragonMapper;
import com.ddmtchr.soalab.mapper.PersonMapper;
import com.ddmtchr.soalab.repository.specification.DragonSpecificationFactory;
import com.ddmtchr.soalab.service.DragonService;
import com.ddmtchr.soalab.service.PersonService;
import com.ddmtchr.soalab.validation.EntityFieldValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DragonDtoService {

    private final DragonService dragonService;
    private final PersonDtoService personDtoService;
    private final PersonService personService;
    private final DragonMapper dragonMapper;
    private final PersonMapper personMapper;
    private final EntityFieldValidator entityFieldValidator;

    public DragonResponseDto findById(Long id) {
        return dragonService.findById(id).map(dragonMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Dragon not found"));
    }

    public PagedDragonListDto search(FilterRequestDto filter, Pageable pageable) {
        validatePageable(pageable);
        validateFilter(filter, Dragon.class);

        Specification<Dragon> spec = DragonSpecificationFactory.byFilters(filter);
        Page<Dragon> page = dragonService.findAll(spec, pageable);

        List<DragonResponseDto> dragons = page.getContent().stream()
                .map(dragonMapper::toResponseDto)
                .toList();

        return new PagedDragonListDto(
                dragons,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }

    @Transactional
    public DragonResponseDto save(DragonRequestDto dto) {
        PersonResponseDto personDto = dto.getKiller();
        Person newPerson = null;

        if (personDto != null) {
            newPerson = findOrCreatePerson(personDto);
        }

        Dragon dragon = dragonMapper.toEntity(dto);
        dragon.setKiller(newPerson);
        return dragonMapper.toResponseDto(dragonService.save(dragon));
    }

    @Transactional
    public DragonResponseDto update(Long id, DragonRequestDto dto) {
        Dragon dragon = dragonService.findById(id).orElseThrow(() -> new NotFoundException("Dragon not found"));
        PersonResponseDto personDto = dto.getKiller();
        Person newPerson = null;

        if (personDto != null) {
            newPerson = findOrCreatePerson(personDto);
        }

        dragonMapper.updateDragon(dto, dragon);
        dragon.setKiller(newPerson);
        return dragonMapper.toResponseDto(dragonService.save(dragon));
    }

    @Transactional
    public void delete(Long id) {
        Dragon dragon = dragonService.findById(id).orElseThrow(() -> new NotFoundException("Dragon not found"));
        dragonService.delete(dragon);
    }

    public DragonResponseDto findMinByName() {
        Optional<Dragon> opt = dragonService.findMinByName();
        return opt.map(dragonMapper::toResponseDto).orElse(null);
    }

    public long count() {
        return dragonService.count();
    }

    public List<DragonTypeCountDto> countByType() {
        return dragonService.countByType();
    }

    public long countByTypeGreater(DragonType type) {
        return dragonService.countByTypeGreater(type);
    }

    private void validatePageable(Pageable pageable) {
        if (pageable != null) {
            if (pageable.getPageNumber() < 0) {
                throw new PageableValidationException("Page number must be >= 0");
            }
            if (pageable.getPageSize() <= 0) {
                throw new PageableValidationException("Page size must be > 0");
            }
        }
    }

    private void validateFilter(FilterRequestDto filterDto, Class<?> entityClass) {
        if (filterDto == null || filterDto.getFilters() == null || filterDto.getFilters().isEmpty()) return;

        List<String> errors = new ArrayList<>();

        for (FilterCriteria f : filterDto.getFilters()) {
            if (!entityFieldValidator.isValidFieldPath(entityClass, f.getField())) {
                errors.add("Filtering by field '%s' unavailable for entity %s"
                        .formatted(f.getField(), entityClass.getSimpleName()));
            } else {
                if (FilterOperation.LIKE.equals(f.getOp())) {
                    Class<?> fieldType = entityFieldValidator.getFieldType(entityClass, f.getField());
                    if (fieldType != String.class) {
                        errors.add("LIKE operation can only be used with string fields. Field '%s' has type %s"
                                .formatted(f.getField(), fieldType.getSimpleName()));
                    }
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new FilterValidationException(String.join(" \n", errors));
        }
    }

    private Person findOrCreatePerson(PersonResponseDto personDto) {
        return personService.findById(personDto.getId())
                .orElseGet(() -> {
                    PersonResponseDto saved = personDtoService.save(
                            personMapper.responseDtoToRequestDto(personDto)
                    );
                    return personMapper.toEntity(saved);
                });
    }
}
