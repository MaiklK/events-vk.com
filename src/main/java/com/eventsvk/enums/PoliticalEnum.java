package com.eventsvk.enums;

public enum PoliticalEnum {
    COMMUNIST(1, "коммунистические"),
    SOCIALIST(2, "социалистические"),
    MODERATE(3, "умеренные"),
    LIBERAL(4, "либеральные"),
    CONSERVATIVE(5, "консервативные"),
    MONARCHICAL(6, "монархические"),
    ULTRACONSERVATIVE(7, "ультраконсервативные"),
    INDIFFERENT(8, "индифферентные"),
    LIBERTARIAN(9, "либертарианские");

    private final int id;
    private final String title;

    PoliticalEnum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitleById(int id) {
        for (PoliticalEnum value : PoliticalEnum.values()) {
            if (value.id == id) {
                return value.title;
            }
        }
        return null;
    }
}
