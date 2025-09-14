package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.api.PageableEntity;
import com.ddmtchr.soalab.dto.api.ApiErrorResponse;
import com.ddmtchr.soalab.dto.api.ApiNumberResponse;
import com.ddmtchr.soalab.dto.api.filter.FilterRequestDto;
import com.ddmtchr.soalab.dto.dragon.*;
import com.ddmtchr.soalab.entity.Dragon;
import com.ddmtchr.soalab.service.DragonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/dragon")
@Tag(name = "Dragon API", description = "Управление коллекцией драконов")
public class DragonController {

    private final DragonService dragonService;

    @PostMapping(consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Создать нового дракона",
            description = "Добавляет нового дракона в коллекцию. ID и дата создания генерируются автоматически.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Дракон успешно создан",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = DragonResponseDto.class))),
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
                                                  <path>/soa/api/v1/dragon</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Неверные входные данные (например, пустое имя или age <= 0)",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>UNPROCESSABLE_ENTITY</status>
                                                  <timestamp>2025-09-13T14:55:27.6973344</timestamp>
                                                  <path>/soa/api/v1/dragon</path>
                                                  <messages>
                                                      <message>Field 'age': должно быть не меньше 1</message>
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
                                                  <path>/soa/api/v1/dragon</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<DragonResponseDto> create(@RequestBody @Valid DragonRequestDto dto) {
        return new ResponseEntity<>(new DragonResponseDto(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить дракона по ID",
            description = "Возвращает объект дракона по уникальному идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Дракон найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = DragonResponseDto.class))),
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
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Дракон с указанным ID не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>Dragon not found</message>
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
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<DragonResponseDto> getById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(new DragonResponseDto());
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_XML_VALUE, consumes = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Обновить дракона по ID",
            description = "Полностью заменяет данные дракона новыми. ID и дата создания остаются прежними.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Дракон обновлён",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = DragonResponseDto.class))),
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
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Дракон с указанным ID не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>Dragon not found</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(responseCode = "422",
                            description = "Неверные входные данные (например, пустое имя или age <= 0)",
                            content = @Content(mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>UNPROCESSABLE_ENTITY</status>
                                                  <timestamp>2025-09-13T14:55:27.6973344</timestamp>
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                      <message>Field 'age': должно быть не меньше 1</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(responseCode = "500",
                            description = "Внутренняя ошибка сервера",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>INTERNAL_SERVER_ERROR</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<DragonResponseDto> update(@PathVariable @Valid Long id, @RequestBody @Valid DragonRequestDto dto) {
        return ResponseEntity.ok(new DragonResponseDto());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить дракона по ID",
            description = "Удаляет дракона из коллекции по идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Дракон удалён",
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
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Дракон не найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>NOT_FOUND</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>Dragon not found</message>
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
                                                  <path>/soa/api/v1/dragon/1</path>
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

    @PostMapping(value = "/search", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить список драконов",
            description = """
                Возвращает страницу драконов с возможностью фильтрации и сортировки.

                **Фильтрация**: передаётся в виде объекта фильтра в теле запроса.

                **Пагинация и сортировка**: используются стандартные параметры Spring Data:
                - `page` — номер страницы (0 по умолчанию)
                - `size` — количество элементов на страницу (20 по умолчанию)
                - `sort` — сортировка, например `sort=name,asc` или `sort=age,desc`
                """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Страница драконов найдена",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = PagedDragonListDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный формат запроса или параметры сортировки/пагинации",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>BAD_REQUEST</status>
                                                  <timestamp>2025-09-14T12:00:54.8718241</timestamp>
                                                  <path>/soa/api/v1/dragon/search</path>
                                                  <messages>
                                                    <message>JSON parse error</message>
                                                  </messages>
                                                </error>
                                                """
                                    ))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Некорректный формат запроса или параметры фильтрации",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>UNPROCESSABLE_ENTITY</status>
                                                  <timestamp>2025-09-14T15:29:35.4584707</timestamp>
                                                  <path>/soa/api/v1/dragon/search</path>
                                                  <messages>
                                                    <message>Failed to convert param with value 'E' to type: 'FilterOperation'</message>
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
                                                  <path>/soa/api/v1/dragon/search</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<PagedDragonListDto> search(
            @RequestBody(required = false) @Valid FilterRequestDto filter,
            @ParameterObject @Valid @PageableDefault @PageableEntity(entityClass = Dragon.class) Pageable pageable) {
        PagedDragonListDto page = dragonService.search(filter, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/name/min", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Найти дракона с минимальным именем",
            description = "Возвращает одного дракона, у которого поле `name` является лексикографически минимальным.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Дракон найден",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = DragonResponseDto.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Коллекция пуста, драконов нет",
                            content = @Content),
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
                                                  <path>/soa/api/v1/dragon/name/min</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<DragonResponseDto> getMinByName() {
        return ResponseEntity.ok(new DragonResponseDto());
    }

    @GetMapping(value = "/type/count", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Группировка по типу",
            description = "Считает количество драконов для каждого значения поля `type`.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Результат группировки",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = DragonTypeCountListDto.class))),
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
                                                  <path>/soa/api/v1/dragon/type/count</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<DragonTypeCountListDto> countByType() {
        return ResponseEntity.ok(new DragonTypeCountListDto(
                List.of(
                        new DragonTypeCountDto(DragonType.AIR, 1L),
                        new DragonTypeCountDto(DragonType.FIRE, 3L)
                        )));
    }

    @GetMapping(value = "/type/count/greater", produces = APPLICATION_XML_VALUE)
    @Operation(
            summary = "Подсчитать количество драконов с типом больше заданного",
            description = "Считает количество элементов, у которых `type` лексикографически больше переданного значения.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Количество найдено",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiNumberResponse.class))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Коллекция пуста, драконов нет",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный формат запроса или некорректное значение параметра type",
                            content = @Content(
                                    mediaType = APPLICATION_XML_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                <error>
                                                  <status>BAD_REQUEST</status>
                                                  <timestamp>2025-09-14T11:58:48.0675202</timestamp>
                                                  <path>/soa/api/v1/dragon/1</path>
                                                  <messages>
                                                    <message>Failed to convert param 'type' with value: 'NOPE'</message>
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
                                                  <path>/soa/api/v1/dragon/type/count/greater</path>
                                                  <messages>
                                                    <message>Internal Server Error</message>
                                                  </messages>
                                                </error>
                                                """
                                    )))
            }
    )
    public ResponseEntity<ApiNumberResponse> countByTypeGreater(@RequestParam @Valid DragonType type) {
//        throw new RuntimeException();
        return ResponseEntity.ok(new ApiNumberResponse(0L));
    }

}
