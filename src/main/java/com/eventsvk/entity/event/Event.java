package com.eventsvk.entity.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "events")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event implements Comparable<Event> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String uuid;
    @Column(name = "event_vkid")
    private String id;
    @Column
    private String name;
    @Column(name = "screen_name")
    private String screenName;
    @Column
    private String description;
    @Column(name = "has_photo")
    private int hasPhoto;
    @Column(name = "members_count")
    private int membersCount;
    @Column(name = "public_date_label")
    private String publicDateLabel;
    @Column
    private String site;
    @Column
    private String activity;
    @Column(name = "start_date ")
    private int startDate;
    @Column(name = "finish_date")
    private int finishDate;
    @Column
    private String status;
    @Column(name = "city_id")
    private int cityId;

//    @Column
//    private EventCounters counters;
    //TODO дописать поля


    @Override
    public int hashCode() {
        return Integer.parseInt(this.id);
    }

    @Override
    public boolean equals(Object event) {
        if (this.getClass() != event.getClass()) {
            return false;
        }
        Event otherEvent = (Event) event;
        return this.id.equals(otherEvent.id);
    }

    @Override
    public int compareTo(@NotNull Event event) {
            return this.startDate - event.startDate;
    }
}
