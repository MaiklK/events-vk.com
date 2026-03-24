package com.eventsvk.enums;

import lombok.Getter;

@Getter
public enum Smoking {
    SHARPLY_NEGATIVE(1, "резко негативное"),
    NEGATIVE(2, "негативное"),
    COMPROMISE(3, "компромиссное"),
    NEUTRAL(4, "нейтральное"),
    POSITIVE(5, "положительное");

    private final int code;
    private final String description;

    Smoking(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
