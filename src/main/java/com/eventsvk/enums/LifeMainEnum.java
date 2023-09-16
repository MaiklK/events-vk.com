package com.eventsvk.enums;

public enum LifeMainEnum {
    FAMILY_AND_CHILDREN(1, "семья и дети"),
    CAREER_AND_MONEY(2, "карьера и деньги"),
    ENTERTAINMENT_AND_RECREATION(3, "развлечения и отдых"),
    SCIENCE_AND_RESEARCH(4, "наука и исследования"),
    IMPROVING_THE_WORLD(5, "совершенствование мира"),
    SELF_DEVELOPMENT(6, "саморазвитие"),
    BEAUTY_AND_ART(7, "красота и искусство"),
    FAME_AND_INFLUENCE(8, "слава и влияние");

    private final int id;
    private final String title;

    LifeMainEnum(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitleById(int id) {
        for (LifeMainEnum value : LifeMainEnum.values()) {
            if (value.id == id) {
                return value.title;
            }
        }
        return null;
    }
}
