package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.*;
import com.ddmtchr.soalab.service.DragonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/dragon")
@Tag(name = "Dragon API", description = "Управление коллекцией драконов")
public class DragonController {

    private final DragonService dragonService;

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Создать нового дракона",
            description = "Добавляет нового дракона в коллекцию. ID и дата создания генерируются автоматически.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Дракон успешно создан",
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = DragonDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные входные данные (например, пустое имя или age <= 0)", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Конфликт — дракон с таким id уже существует", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<DragonDto> create(@RequestBody @Valid DragonDto dto) {
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить дракона по ID",
            description = "Возвращает объект дракона по уникальному идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Дракон найден",
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = DragonDto.class))),
                    @ApiResponse(responseCode = "404", description = "Дракон с указанным ID не найден", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<DragonDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new DragonDto());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Обновить дракона по ID",
            description = "Полностью заменяет данные дракона новыми. ID и дата создания остаются прежними.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Дракон обновлён",
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = DragonDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Дракон с таким ID не найден", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<DragonDto> update(@PathVariable Long id, @RequestBody @Valid DragonDto dto) {
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить дракона по ID",
            description = "Удаляет дракона из коллекции по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Дракон удалён"),
                    @ApiResponse(responseCode = "404", description = "Дракон не найден", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Получить список драконов",
            description = """
                Возвращает страницу драконов с возможностью фильтрации и сортировки.

                **Фильтрация**: передаётся в виде query-параметров (например: `?name=Drakon&type=FIRE`).

                **Пагинация и сортировка**: используются стандартные параметры Spring Data:
                - `page` — номер страницы (0 по умолчанию)
                - `size` — количество элементов на страницу (20 по умолчанию)
                - `sort` — сортировка, например `sort=name,asc` или `sort=age,desc`
                """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница драконов найдена",
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = PagedDragonListDto.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные параметры фильтрации/сортировки/пагинации", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<PagedDragonListDto> getAll(
            @Parameter(description = "Фильтры по полям (например: name=Drakon&type=FIRE)")
            @RequestParam(required = false) Map<String, String> filters,
            @ParameterObject Pageable pageable
    ) {
        List<DragonDto> dragons = List.of(new DragonDto(), new DragonDto());
        long total = 2L;

        PagedDragonListDto response = new PagedDragonListDto(dragons, pageable.getPageNumber(), pageable.getPageSize(), total);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/min-by-name", produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Найти дракона с минимальным именем",
            description = "Возвращает одного дракона, у которого поле `name` является лексикографически минимальным.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Дракон найден",
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = DragonDto.class))),
                    @ApiResponse(responseCode = "204", description = "Коллекция пуста, драконов нет", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<DragonDto> getMinByName() {
        return ResponseEntity.ok(new DragonDto());
    }

    @GetMapping(value = "/group-by-type", produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(
            summary = "Группировка по типу",
            description = "Считает количество драконов для каждого значения поля `type`.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Результат группировки",
                            content = @Content(mediaType = "application/xml", schema = @Schema(implementation = DragonTypeCountListDto.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<DragonTypeCountListDto> groupByType() {
        return ResponseEntity.ok(new DragonTypeCountListDto(List.of(new DragonTypeCountDto(DragonType.AIR, 1L))));
    }

    @GetMapping("/count-by-type-greater")
    @Operation(
            summary = "Подсчитать количество драконов с типом больше заданного",
            description = "Считает количество элементов, у которых `type` лексикографически больше переданного значения.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Количество найдено",
                            content = @Content(schema = @Schema(implementation = Long.class))),
                    @ApiResponse(responseCode = "204", description = "Коллекция пуста, драконов нет", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Некорректное значение параметра type", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<Long> countByTypeGreater(@RequestParam @Valid DragonType type) {
        return ResponseEntity.ok(0L);
    }

}
