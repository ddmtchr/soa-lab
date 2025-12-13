package com.ddmtchr.api.dto.api;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum FilterOperation implements Serializable {
    EQ("=="),
    NE("!="),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    LIKE("~");

    private final String value;

    FilterOperation(String value) {
        this.value = value;
    }
}
