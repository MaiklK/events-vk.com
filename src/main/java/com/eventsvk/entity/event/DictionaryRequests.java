package com.eventsvk.entity.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_requests")
@Getter
@Setter
@NoArgsConstructor
public class DictionaryRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column
    private String request;
}
