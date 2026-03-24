package com.eventsvk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "events")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventEntity implements Comparable<EventEntity> {
    @Id
    private Long eventId;
    @Column
    private String name;
    @Column
    private String screenName;
    @Column
    private String description;
    @Column
    private int hasPhoto;
    @Column
    private int membersCount;
    @Column
    private String publicDateLabel;
    @Column
    private String site;
    @Column
    private String activity;
    @Column
    private int startDate;
    @Column
    private int finishDate;
    @Column
    private String status;
    @Column
    private int cityId;

//    @Column
//    private EventCounters counters;
    //TODO дописать поля


    @Override
    public int hashCode() {
        return Objects.hash(this.eventId);
    }

    @Override
    public boolean equals(Object event) {
        if (this.getClass() != event.getClass()) {
            return false;
        }
        EventEntity otherEventEntity = (EventEntity) event;
        return this.eventId.equals(otherEventEntity.eventId);
    }

    @Override
    public int compareTo(EventEntity eventEntity) {
        return this.startDate - eventEntity.startDate;
    }
}
