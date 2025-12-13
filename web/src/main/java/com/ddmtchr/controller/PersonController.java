package com.ddmtchr.controller;

import com.ddmtchr.api.dto.api.ApiErrorResponse;
import com.ddmtchr.api.dto.person.PersonListDto;
import com.ddmtchr.api.dto.person.PersonRequestDto;
import com.ddmtchr.api.dto.person.PersonResponseDto;
import com.ddmtchr.api.service.PersonDtoService;
import com.ddmtchr.jndi.EjbLocator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping(path = "/persons")
@Tag(name = "Person API", description = "Люди")
public class PersonController {

    private final PersonDtoService personService;

    public PersonController(EjbLocator ejbLocator) {
        this.personService = ejbLocator.lookup("ejb:/ejb/PersonDtoServiceImpl!com.ddmtchr.api.service.PersonDtoService", PersonDtoService.class);
    }

    @PostMapping(consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Создать нового человека",
            description = "Добавляет нового человека. ID генерируется автоматически.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Человек успешно создан",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = PersonResponseDto.class))),
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
                                                  <path>/soa/api/v1/persons</path>
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
                                                  <path>/soa/api/v1/persons</path>
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
                                                  <path>/soa/api/v1/persons</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonRequestDto dto) {
        return new ResponseEntity<>(personService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить человека по ID",
            description = "Возвращает объект человека по уникальному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Человек найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = PersonResponseDto.class))),
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
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Человек с указанным ID не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>Person not found</message>
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
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<PersonResponseDto> getById(@PathVariable("id") @Valid Long id) {
        return ResponseEntity.ok(personService.findById(id));
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_XML_VALUE, consumes = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Обновить человека по ID",
            description = "Полностью заменяет данные человека новыми. ID остается прежним.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Человек обновлен",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = PersonResponseDto.class))),
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
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Человек с указанным ID не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>Person not found</message>
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
                                                  <path>/soa/api/v1/persons/1</path>
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
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<PersonResponseDto> update(@PathVariable("id") @Valid Long id, @RequestBody @Valid PersonRequestDto dto) {
        return ResponseEntity.ok(personService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Удалить человека по ID",
            description = "Удаляет человека из коллекции по идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Человек удален",
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
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Человек не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>Person not found</message>
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
                                                  <path>/soa/api/v1/persons/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<Void> delete(@PathVariable("id") @Valid Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить список людей",
            description = "Возвращает список всех людей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список людей найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = PersonListDto.class))),
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
                                                  <path>/soa/api/v1/persons</path>
                                                  <messages>
                                                    <message> Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<PersonListDto> findAll() {
        return ResponseEntity.ok(new PersonListDto(personService.findAll()));
    }

}
