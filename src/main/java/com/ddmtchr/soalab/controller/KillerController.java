package com.ddmtchr.soalab.controller;

import com.ddmtchr.soalab.dto.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/killer")
@Tag(name = "Killer API", description = "Дополнительные операции с убийцами драконов")
public class KillerController {

    @PostMapping("/dragon/{dragonId}/kill")
    @Operation(
            summary = "Убить указанного дракона",
            description = "Отмечает дракона как убитого",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Дракон убит"),
                    @ApiResponse(responseCode = "404", description = "Дракон с таким ID не найден", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "503", description = "Ошибка при обращении к стороннему сервису", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<String> killDragon(@PathVariable Long dragonId) {
        return ResponseEntity.ok("Dragon " + dragonId + " killed");
    }

    @PostMapping("/team/{teamId}/move-to-cave/{caveId}")
    @Operation(
            summary = "Отправить команду в пещеру",
            description = "Перемещает команду убийц драконов в указанную пещеру.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Команда успешно перемещена"),
                    @ApiResponse(responseCode = "404", description = "Команда или пещера не найдены", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "503", description = "Ошибка при обращении к стороннему сервису", content = @Content(mediaType = "application/xml", schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    public ResponseEntity<String> moveTeam(@PathVariable Long teamId, @PathVariable Long caveId) {
        return ResponseEntity.ok("Team " + teamId + " moved to cave " + caveId);
    }
}
