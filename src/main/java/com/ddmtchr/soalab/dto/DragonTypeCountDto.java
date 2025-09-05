package com.ddmtchr.soalab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DragonTypeCountDto {

    private DragonType type;

    private Long count;
}
