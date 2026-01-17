package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.cave.CaveListDto;
import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.ddmtchr.soalab.mapper.CaveMapper;
import com.ddmtchr.soalab.schema.*;
import com.ddmtchr.soalab.service.dto.CaveDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class CaveEndpoint {

    public static final String NAMESPACE = "http://com/ddmtchr/soalab/schema";

    private final CaveDtoService caveService;
    private final CaveMapper caveMapper;

    @PayloadRoot(namespace = NAMESPACE, localPart = "CreateCaveRequest")
    @ResponsePayload
    public CreateCaveResponse create(@RequestPayload CreateCaveRequest request) {
        CaveResponseDto saved = caveService.save(caveMapper.toRequestDto(request.getCave()));

        CreateCaveResponse response = new CreateCaveResponse();
        response.setCave(caveMapper.toResponse(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetCaveByIdRequest")
    @ResponsePayload
    public GetCaveByIdResponse getById(@RequestPayload GetCaveByIdRequest request) {
        GetCaveByIdResponse response = new GetCaveByIdResponse();
        response.setCave(caveMapper.toResponse(caveService.findById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "UpdateCaveRequest")
    @ResponsePayload
    public UpdateCaveResponse update(@RequestPayload UpdateCaveRequest request) {
        CaveResponseDto updated = caveService.update(request.getId(), caveMapper.toRequestDto(request.getCave()));

        UpdateCaveResponse response = new UpdateCaveResponse();
        response.setCave(caveMapper.toResponse(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "DeleteCaveRequest")
    @ResponsePayload
    public DeleteCaveResponse delete(@RequestPayload DeleteCaveRequest request) {
        caveService.delete(request.getId());
        return new DeleteCaveResponse();
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "FindAllCavesRequest")
    @ResponsePayload
    public FindAllCavesResponse findAll(@RequestPayload FindAllCavesRequest request) {
        FindAllCavesResponse response = new FindAllCavesResponse();
        CaveListDto listDto = new CaveListDto(caveService.findAll());
        response.getCave().addAll(
                listDto.getContent().stream().map(caveMapper::toResponse).toList()
        );
        return response;
    }
}
