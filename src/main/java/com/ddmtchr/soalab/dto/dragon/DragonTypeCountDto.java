package com.ddmtchr.soalab.dto.dragon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
public class DragonTypeCountDto {

    private DragonType type;

    private Long count;
}
