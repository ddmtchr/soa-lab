package com.ddmtchr.soalab.service;

import com.ddmtchr.soalab.dto.api.filter.FilterCriteria;
import com.ddmtchr.soalab.dto.api.filter.FilterRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonResponseDto;
import com.ddmtchr.soalab.dto.dragon.PagedDragonListDto;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.exception.FilterValidationException;
import com.ddmtchr.soalab.exception.PageableValidationException;
import com.ddmtchr.soalab.mapper.DragonMapper;
import com.ddmtchr.soalab.repository.DragonRepository;
import com.ddmtchr.soalab.util.ReflectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DragonService {

    private final DragonRepository dragonRepository;
    private final DragonMapper dragonMapper;

    @Transactional
    public DragonResponseDto createDragon(DragonRequestDto requestDto) {
        Dragon entity = dragonMapper.toEntity(requestDto);
        Dragon saved = dragonRepository.save(entity);
        return dragonMapper.toResponseDto(saved);
    }

    public PagedDragonListDto search(FilterRequestDto filter, Pageable pageable) {

        validatePageable(pageable);
        validateFilter(filter, Dragon.class);

        List<DragonResponseDto> dragons = List.of(new DragonResponseDto(), new DragonResponseDto());
        long total = 2L;
        return new PagedDragonListDto(dragons, pageable.getPageNumber(), pageable.getPageSize(), total);
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
        if (filterDto != null) {
            Set<String> classFieldNames = ReflectionUtil.getAllFieldsFromCache(entityClass).stream().map(Field::getName).collect(Collectors.toSet());

            boolean valid = true;
            List<String> messages = new ArrayList<>();
            for (FilterCriteria filter : filterDto.getFilters()) {
                if (!classFieldNames.contains(filter.getField())) {
                    valid = false;
                    messages.add(String.format("Filtering by field '%s' unavailable for entity %s",
                            filter.getField(), entityClass.getSimpleName()));
                }
            }
            if (!valid) {
                throw new FilterValidationException(String.join(" \n", messages));
            }
        }
    }
}
