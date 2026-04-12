package com.eventsvk.entity.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dic_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dic_request_seq_gen")
    @SequenceGenerator(name = "dic_request_seq_gen", sequenceName = "dic_request_seq", allocationSize = 1)
    private int id;
    @Column(unique = true)
    private String req;
}
