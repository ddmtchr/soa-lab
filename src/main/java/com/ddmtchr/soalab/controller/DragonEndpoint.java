package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.api.filter.FilterCriteria;
import com.ddmtchr.soalab.dto.api.filter.FilterRequestDto;
import com.ddmtchr.soalab.dto.dragon.DragonResponseDto;
import com.ddmtchr.soalab.dto.dragon.DragonTypeCountDto;
import com.ddmtchr.soalab.dto.dragon.PagedDragonListDto;
import com.ddmtchr.soalab.mapper.DragonMapper;
import com.ddmtchr.soalab.schema.*;
import com.ddmtchr.soalab.service.dto.DragonDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;
import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class DragonEndpoint {

    public static final String NAMESPACE = "http://com/ddmtchr/soalab/schema";

    private final DragonDtoService dragonService;
    private final DragonMapper dragonMapper;

    @PayloadRoot(namespace = NAMESPACE, localPart = "CreateDragonRequest")
    @ResponsePayload
    public CreateDragonResponse create(@RequestPayload CreateDragonRequest request) {
        DragonResponseDto saved = dragonService.save(dragonMapper.toRequestDto(request.getDragon()));

        CreateDragonResponse response = new CreateDragonResponse();
        response.setDragon(dragonMapper.toResponse(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetDragonByIdRequest")
    @ResponsePayload
    public GetDragonByIdResponse getById(@RequestPayload GetDragonByIdRequest request) {
        GetDragonByIdResponse response = new GetDragonByIdResponse();
        response.setDragon(dragonMapper.toResponse(dragonService.findById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "UpdateDragonRequest")
    @ResponsePayload
    public UpdateDragonResponse update(@RequestPayload UpdateDragonRequest request) {
        DragonResponseDto updated = dragonService.update(request.getId(), dragonMapper.toRequestDto(request.getDragon()));

        UpdateDragonResponse response = new UpdateDragonResponse();
        response.setDragon(dragonMapper.toResponse(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "DeleteDragonRequest")
    @ResponsePayload
    public DeleteDragonResponse delete(@RequestPayload DeleteDragonRequest request) {
        dragonService.delete(request.getId());
        return new DeleteDragonResponse();
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "SearchDragonsRequest")
    @ResponsePayload
    public SearchDragonsResponse search(@RequestPayload SearchDragonsRequest request) {
        if (request.getPage() == null) request.setPage(BigInteger.ZERO);
        if (request.getSize() == null) request.setSize(BigInteger.TEN);

        PagedDragonListDto page = dragonService.search(new FilterRequestDto(
                request.getFilter().stream().map(filter -> new FilterCriteria(
                        filter.getField(),
                        com.ddmtchr.soalab.dto.api.FilterOperation.valueOf(filter.getOp().toString()),
                        filter.getValue()
                )).toList()),
                PageRequest.of(request.getPage().intValue(), request.getSize().intValue(), parseSort(request.getSort()))
        );

        SearchDragonsResponse response = new SearchDragonsResponse();
        response.getDragon().addAll(page.getContent().stream().map(dragonMapper::toResponse).toList());
        response.setPage(BigInteger.valueOf(page.getPage()));
        response.setSize(BigInteger.valueOf(page.getSize()));
        response.setTotal(BigInteger.valueOf(page.getTotal()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetMinByNameDragonRequest")
    @ResponsePayload
    public GetMinByNameDragonResponse getMinByName(@RequestPayload GetMinByNameDragonRequest request) {
        DragonResponseDto found = dragonService.findMinByName();
        GetMinByNameDragonResponse response = new GetMinByNameDragonResponse();
        if (found != null) {
            response.setDragon(dragonMapper.toResponse(found));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "CountByTypeDragonRequest")
    @ResponsePayload
    public CountByTypeDragonResponse countByType(@RequestPayload CountByTypeDragonRequest request) {
        List<DragonTypeCountDto> counts = dragonService.countByType();
        CountByTypeDragonResponse response = new CountByTypeDragonResponse();
        if (!counts.isEmpty()) {
            response.getEntry().addAll(
                    counts.stream().map(dto -> {
                        DragonTypeCount count = new DragonTypeCount();
                        count.setCount(dto.getCount());
                        count.setType(DragonType.valueOf(dto.getType().toString()));
                        return count;
                    }).toList()
            );
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "CountByTypeGreaterDragonRequest")
    @ResponsePayload
    public CountByTypeGreaterDragonResponse countByTypeGreater(@RequestPayload CountByTypeGreaterDragonRequest request) {
        CountByTypeGreaterDragonResponse response = new CountByTypeGreaterDragonResponse();
        if (dragonService.count() > 0) {
            response.setNumber(dragonService.countByTypeGreater(com.ddmtchr.soalab.dto.dragon.DragonType.valueOf(request.getType().toString())));
        }
        return response;
    }

    private Sort parseSort(String sortString) {
        if (sortString == null || sortString.isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sortString.split(",");

        if (parts.length == 1) {
            return Sort.by(Sort.Direction.ASC, parts[0].trim());
        }

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid sort format. Expected: 'field,direction'");
        }

        String field = parts[0].trim();
        String directionStr = parts[1].trim().toLowerCase();

        Sort.Direction direction;
        if (directionStr.equals("asc")) {
            direction = Sort.Direction.ASC;
        } else if (directionStr.equals("desc")) {
            direction = Sort.Direction.DESC;
        } else {
            throw new IllegalArgumentException("Invalid direction. Use 'asc' or 'desc'");
        }

        return Sort.by(direction, field);
    }
}
