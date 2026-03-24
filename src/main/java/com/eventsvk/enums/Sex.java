package com.eventsvk.enums;

import lombok.Getter;

@Getter
public enum Sex {
    FEMALE(1, "женский"),
    MALE(2, "мужской"),
    GENDER_IS_NOT_SPECIFIED(0, "пол не указан");

    private final int code;
    private final String description;

    Sex(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
