package com.ddmtchr.soalab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "dragons")
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@Getter
@Setter
public class DragonListDto {

    @XmlElement(name = "dragon")
    private List<DragonDto> dragons;
}
