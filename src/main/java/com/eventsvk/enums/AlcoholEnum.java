package com.eventsvk.enums;

public enum AlcoholEnum {

    SHARPLY_NEGATIVE(1, "резко негативное"),
    NEGATIVE(2, "негативное"),
    COMPROMISE(3, "компромиссное"),
    NEUTRAL(4, "нейтральное"),
    POSITIVE(5, "позитивное");

    private final int id;
    private final String title;

    AlcoholEnum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitleById(int id) {
        for (AlcoholEnum value : AlcoholEnum.values()) {
            if (value.id == id) {
                return value.title;
            }
        }
        return null;
    }
}
