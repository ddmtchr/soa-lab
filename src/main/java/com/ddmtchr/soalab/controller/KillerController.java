package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.api.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/killer")
@Tag(name = "Killer API", description = "Дополнительные операции с убийцами драконов")
public class KillerController {

    @PatchMapping(value = "/dragon/{dragonId}/kill", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Убить указанного дракона",
            description = "Устанавливает дракону убийцу",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Дракон убит",
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
                                                  <timestamp>2025-09-15T11:03:25.4214021</timestamp>
                                                  <path>/soa/api/v1/killer/dragon/1/kill</path>
                                                  <messages>
                                                    <message>Failed to convert param 'killerId' with value: 'a'</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Дракон или убийца с таким ID не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/killer/dragon/1/kill</path>
                                                  <messages>
                                                    <message>Dragon not found</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Дракон уже убит",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>CONFLICT</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/killer/dragon/1/kill</path>
                                                  <messages>
                                                    <message>Dragon is already killed</message>
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
                                                  <path>/soa/api/v1/killer/dragon/1/kill</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Ошибка при обращении к стороннему сервису",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>SERVICE_UNAVAILABLE</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/killer/dragon/1/kill</path>
                                                  <messages>
                                                    <message>Error while accessing external service</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<Void> killDragon(@Parameter(name = "dragonId", description = "ID дракона") @PathVariable @Valid Long dragonId,
                                           @Parameter(name = "killerId", description = "ID убийцы") @RequestParam("killerId") @Valid Long killerId) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/team/{teamId}/move-to-cave/{caveId}", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Отправить команду в пещеру",
            description = "Перемещает команду убийц драконов в указанную пещеру.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Команда успешно перемещена",
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
                                                  <timestamp>2025-09-15T11:03:25.4214021</timestamp>
                                                  <path>/soa/api/v1/killer/team/a/move-to-cave/1</path>
                                                  <messages>
                                                    <message>Failed to convert param 'teamId' with value: 'a'</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Команда или пещера не найдены",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/killer/team/1/move-to-cave/1</path>
                                                  <messages>
                                                    <message>Team not found</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Команда уже в указанной пещере",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>CONFLICT</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>//soa/api/v1/killer/team/1/move-to-cave/1</path>
                                                  <messages>
                                                    <message>Team is already in specified cave</message>
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
                                                  <path>/soa/api/v1/killer/team/1/move-to-cave/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Ошибка при обращении к стороннему сервису",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>SERVICE_UNAVAILABLE</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/killer/team/1/move-to-cave/1</path>
                                                  <messages>
                                                    <message>Error while accessing external service</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<Void> moveTeam(
            @Parameter(name = "teamId", description = "ID команды") @PathVariable @Valid Long teamId,
            @Parameter(name = "caveId", description = "ID пещеры") @PathVariable @Valid Long caveId) {
        return ResponseEntity.noContent().build();
    }
}
