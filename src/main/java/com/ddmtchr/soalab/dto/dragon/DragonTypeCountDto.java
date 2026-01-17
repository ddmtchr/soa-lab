package com.ddmtchr.soalab.dto.dragon;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Entry")
@XmlRootElement(name = "entry")
@AllArgsConstructor
@Getter
@Setter
public class DragonTypeCountDto {

    @XmlElement(required = true)
    private DragonType type;

    @XmlElement(required = true)
    private Long count;
}
