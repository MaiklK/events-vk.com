package com.eventsvk.enums;

public enum SexEnum {
    FEMALE(1, "женский"),
    MALE(2, "мужской"),
    GENDER_IS_NOT_SPECIFIED(0, "пол не указан");

    private final int id;
    private final String title;

    SexEnum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitleById(int id) {
        for (SexEnum value : SexEnum.values()) {
            if (value.id == id) {
                return value.title;
            }
        }
        return null;
    }
}
