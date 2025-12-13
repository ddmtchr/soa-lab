package com.ddmtchr.api.dto.dragon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
public class DragonTypeCountDto implements Serializable {

    private DragonType type;

    private Long count;
}
