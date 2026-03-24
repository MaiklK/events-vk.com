package com.eventsvk.enums;

import lombok.Getter;

@Getter
public enum Alcohol {

    SHARPLY_NEGATIVE(1, "резко негативное"),
    NEGATIVE(2, "негативное"),
    COMPROMISE(3, "компромиссное"),
    NEUTRAL(4, "нейтральное"),
    POSITIVE(5, "позитивное");

    private final int id;
    private final String title;

    Alcohol(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
