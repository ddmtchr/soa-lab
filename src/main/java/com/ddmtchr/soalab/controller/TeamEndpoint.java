package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.team.TeamListDto;
import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import com.ddmtchr.soalab.mapper.TeamMapper;
import com.ddmtchr.soalab.schema.*;
import com.ddmtchr.soalab.service.dto.TeamDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class TeamEndpoint {
    
    public static final String NAMESPACE = "http://com/ddmtchr/soalab/schema";

    private final TeamDtoService teamService;
    private final TeamMapper teamMapper;

    @PayloadRoot(namespace = NAMESPACE, localPart = "CreateTeamRequest")
    @ResponsePayload
    public CreateTeamResponse create(@RequestPayload CreateTeamRequest request) {
        TeamResponseDto saved = teamService.save(teamMapper.toRequestDto(request.getTeam()));

        CreateTeamResponse response = new CreateTeamResponse();
        response.setTeam(teamMapper.toResponse(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "GetTeamByIdRequest")
    @ResponsePayload
    public GetTeamByIdResponse getById(@RequestPayload GetTeamByIdRequest request) {
        GetTeamByIdResponse response = new GetTeamByIdResponse();
        response.setTeam(teamMapper.toResponse(teamService.findById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "UpdateTeamRequest")
    @ResponsePayload
    public UpdateTeamResponse update(@RequestPayload UpdateTeamRequest request) {
        TeamResponseDto updated = teamService.update(request.getId(), teamMapper.toRequestDto(request.getTeam()));

        UpdateTeamResponse response = new UpdateTeamResponse();
        response.setTeam(teamMapper.toResponse(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "DeleteTeamRequest")
    @ResponsePayload
    public DeleteTeamResponse delete(@RequestPayload DeleteTeamRequest request) {
        teamService.delete(request.getId());
        return new DeleteTeamResponse();
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "FindAllTeamsRequest")
    @ResponsePayload
    public FindAllTeamsResponse findAll(@RequestPayload FindAllTeamsRequest request) {
        FindAllTeamsResponse response = new FindAllTeamsResponse();
        TeamListDto listDto = new TeamListDto(teamService.findAll());
        response.getTeam().addAll(
                listDto.getContent().stream().map(teamMapper::toResponse).toList()
        );
        return response;
    }
}
