package com.ddmtchr.soalab.dto.api;

import lombok.Getter;

@Getter
public enum FilterOperation {
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
