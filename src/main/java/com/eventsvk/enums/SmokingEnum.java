package com.eventsvk.enums;

public enum SmokingEnum {
    SHARPLY_NEGATIVE(1, "резко негативное"),
    NEGATIVE(2, "негативное"),
    COMPROMISE(3, "компромиссное"),
    NEUTRAL(4, "нейтральное"),
    POSITIVE(5, "положительное");

    private final int id;
    private final String title;

    SmokingEnum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitleById(int id) {
        for (SmokingEnum value : SmokingEnum.values()) {
            if (value.id == id) {
                return value.title;
            }
        }
        return null;
    }
}
