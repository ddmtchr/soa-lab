package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.api.ApiErrorResponse;
import com.ddmtchr.soalab.dto.cave.CaveResponseDto;
import com.ddmtchr.soalab.dto.team.TeamListDto;
import com.ddmtchr.soalab.dto.team.TeamRequestDto;
import com.ddmtchr.soalab.dto.team.TeamResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/teams")
@Tag(name = "Team API", description = "Команды")
public class TeamController {

    @PostMapping(consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Создать новую команду",
            description = "Добавляет новую команду. ID генерируется автоматически.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Команда успешно создана",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = TeamResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат запроса",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>BAD_REQUEST</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Неверные входные данные (например, пустое имя)",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>UNPROCESSABLE_ENTITY</status>
                                                  <timestamp>2025-09-13T14:55:27.6973344</timestamp>
                                                  <path>/soa/api/v1/team</path>
                                                  <messages>
                                                      <message>Field 'name': должно быть заполнено</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>INTERNAL_SERVER_ERROR</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/team</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<TeamResponseDto> create(@RequestBody @Valid TeamRequestDto dto) {
        return new ResponseEntity<>(new TeamResponseDto(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить команду по ID",
            description = "Возвращает объект команды по уникальному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Команда найдена",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = TeamResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат запроса",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>BAD_REQUEST</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Команда с указанным ID не найдена",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>Team not found</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>INTERNAL_SERVER_ERROR</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<TeamResponseDto> getById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(new TeamResponseDto());
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_XML_VALUE, consumes = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Обновить команду по ID",
            description = "Полностью заменяет данные команды новыми. ID остается прежним.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Команда обновлена",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = TeamResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат запроса",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>BAD_REQUEST</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Команда с указанным ID не найдена",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>Team not found</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Неверные входные данные (например, пустое имя)",
                            content = @Content(mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>UNPROCESSABLE_ENTITY</status>
                                                  <timestamp>2025-09-13T14:55:27.6973344</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                      <message>Field 'name': должно быть заполнено</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>INTERNAL_SERVER_ERROR</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<TeamResponseDto> update(@PathVariable @Valid Long id, @RequestBody @Valid TeamRequestDto dto) {
        return ResponseEntity.ok(new TeamResponseDto());
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Удалить команду по ID",
            description = "Удаляет команду из коллекции по идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Команда удалена",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат запроса",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>BAD_REQUEST</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Команда не найдена",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>Team not found</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>INTERNAL_SERVER_ERROR</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/team/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить список команд",
            description = "Возвращает список всех команд",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список команд найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = TeamListDto.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>INTERNAL_SERVER_ERROR</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/team</path>
                                                  <messages>
                                                    <message> Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<TeamListDto> findAll() {
        TeamListDto list = new TeamListDto(List.of(new TeamResponseDto(1L, "Name", new CaveResponseDto(2L, "name"))));

        return ResponseEntity.ok(list);
    }
}
