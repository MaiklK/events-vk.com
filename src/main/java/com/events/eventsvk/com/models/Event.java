package com.events.eventsvk.com.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event")
    @SequenceGenerator(name = "seq_event", allocationSize = 1)
    private long id;
    @Column(name = "name")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 40 знаков")
    private String name;
    @NotEmpty(message = "Id мероприятия не должно быть пустым")
    @Column(name = "id_event")
    private String eventId;

    public Event() {
    }

    public Event(String name, String idEvent) {
        this.name = name;
        this.eventId = idEvent;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String idEvent) {
        this.eventId = idEvent;
    }
}
