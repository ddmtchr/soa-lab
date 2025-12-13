package com.ddmtchr.service.dto;

import com.ddmtchr.api.dto.api.FilterOperation;
import com.ddmtchr.api.dto.api.filter.FilterCriteria;
import com.ddmtchr.api.dto.api.filter.FilterRequestDto;
import com.ddmtchr.api.dto.dragon.*;
import com.ddmtchr.api.dto.person.PersonResponseDto;
import com.ddmtchr.api.exception.FilterValidationException;
import com.ddmtchr.api.exception.NotFoundException;
import com.ddmtchr.api.exception.PageableValidationException;
import com.ddmtchr.api.service.DragonDtoService;
import com.ddmtchr.entity.Dragon;
import com.ddmtchr.entity.Person;
import com.ddmtchr.mapper.DragonMapper;
import com.ddmtchr.mapper.PersonMapper;
import com.ddmtchr.repository.specification.DragonSpecificationFactory;
import com.ddmtchr.repository.specification.Specification;
import com.ddmtchr.service.DragonService;
import com.ddmtchr.service.PersonService;
import com.ddmtchr.validation.EntityFieldValidatorLocal;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@Remote(DragonDtoService.class)
@Local(DragonDtoServiceLocal.class)
public class DragonDtoServiceImpl implements DragonDtoServiceLocal {

    @Inject
    private DragonService dragonService;

    @Inject
    private PersonDtoServiceLocal personDtoService;

    @Inject
    private PersonService personService;

    @Inject
    private DragonMapper dragonMapper;

    @Inject
    private PersonMapper personMapper;

    @Inject
    private EntityFieldValidatorLocal entityFieldValidator;

    @Override
    public DragonResponseDto findById(Long id) {
        return dragonService.findById(id).map(dragonMapper::toResponseDto).orElseThrow(() -> new NotFoundException("Dragon not found"));
    }

    @Override
    public PagedDragonListDto search(FilterRequestDto filter, int page, int size, String sort) {
        if (page < 0) {
            throw new PageableValidationException("Page number must be >= 0");
        }
        if (size <= 0) {
            throw new PageableValidationException("Page size must be > 0");
        }

        String fieldPath = sort.split(",")[0];
        validateSort(fieldPath, Dragon.class);
        validateFilter(filter, Dragon.class);

        Specification<Dragon> spec = DragonSpecificationFactory.byFilters(filter);
        List<Dragon> content = dragonService.findAll(spec, page, size, sort);

        List<DragonResponseDto> dragons = content.stream()
                .map(dragonMapper::toResponseDto)
                .toList();

        return new PagedDragonListDto(
                dragons,
                page,
                dragons.size(),
                dragonService.count()
        );
    }

    @Transactional
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        Dragon dragon = dragonService.findById(id).orElseThrow(() -> new NotFoundException("Dragon not found"));
        dragonService.delete(dragon);
    }

    @Override
    public DragonResponseDto findMinByName() {
        Optional<Dragon> opt = dragonService.findMinByName();
        return opt.map(dragonMapper::toResponseDto).orElse(null);
    }

    @Override
    public long count() {
        return dragonService.count();
    }

    @Override
    public List<DragonTypeCountDto> countByType() {
        return dragonService.countByType();
    }

    @Override
    public long countByTypeGreater(DragonType type) {
        return dragonService.countByTypeGreater(type);
    }

    private void validateSort(String sort, Class<?> entityClass) {
        if (sort != null) {
            if (!sort.equals("UNSORTED")) {
                if (!entityFieldValidator.isValidFieldPath(entityClass, sort)) {
                    throw new FilterValidationException("Sorting by field '%s' unavailable for entity %s"
                            .formatted(sort, entityClass.getSimpleName()));
                }
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
