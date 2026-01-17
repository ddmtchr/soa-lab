package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.person.PersonListDto;
import com.ddmtchr.soalab.dto.person.PersonResponseDto;
import com.ddmtchr.soalab.mapper.PersonMapper;
import com.ddmtchr.soalab.schema.*;
import com.ddmtchr.soalab.service.dto.PersonDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class PersonEndpoint {
    public static final String NAMESPACE = "http://com/ddmtchr/soalab/schema";

    private final PersonDtoService personService;
    private final PersonMapper personMapper;

    @PayloadRoot(namespace = NAMESPACE, localPart = "CreatePersonRequest")
    @ResponsePayload
    public CreatePersonResponse create(@RequestPayload CreatePersonRequest request) {
        PersonResponseDto saved = personService.save(personMapper.toRequestDto(request.getPerson()));

        CreatePersonResponse response = new CreatePersonResponse();
        response.setPerson(personMapper.toResponse(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetPersonByIdRequest")
    @ResponsePayload
    public GetPersonByIdResponse getById(@RequestPayload GetPersonByIdRequest request) {
        GetPersonByIdResponse response = new GetPersonByIdResponse();
        response.setPerson(personMapper.toResponse(personService.findById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "UpdatePersonRequest")
    @ResponsePayload
    public UpdatePersonResponse update(@RequestPayload UpdatePersonRequest request) {
        PersonResponseDto updated = personService.update(request.getId(), personMapper.toRequestDto(request.getPerson()));

        UpdatePersonResponse response = new UpdatePersonResponse();
        response.setPerson(personMapper.toResponse(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "DeletePersonRequest")
    @ResponsePayload
    public DeletePersonResponse delete(@RequestPayload DeletePersonRequest request) {
        personService.delete(request.getId());
        return new DeletePersonResponse();
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "FindAllPersonsRequest")
    @ResponsePayload
    public FindAllPersonsResponse findAll(@RequestPayload FindAllPersonsRequest request) {
        FindAllPersonsResponse response = new FindAllPersonsResponse();
        PersonListDto listDto = new PersonListDto(personService.findAll());
        response.getPerson().addAll(
                listDto.getContent().stream().map(personMapper::toResponse).toList()
        );
        return response;
    }
}
