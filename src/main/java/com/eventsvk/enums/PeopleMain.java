package com.eventsvk.enums;

import lombok.Getter;

@Getter
public enum PeopleMain {
    INTELLIGENCE_AND_CREATIVITY(1, "ум и креативность"),
    KINDNESS_AND_HONESTY(2, "доброта и честность"),
    BEAUTY_AND_HEALTH(3, "красота и здоровье"),
    POWER_AND_WEALTH(4, "власть и богатство"),
    COURAGE_AND_PERSEVERANCE(5, "смелость и упорство"),
    HUMOR_AND_LOVE_OF_LIFE(6, "юмор и жизнелюбие");

    private final int id;
    private final String title;

    PeopleMain(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
