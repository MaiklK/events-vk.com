package com.eventsvk.entity.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "black_list")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "black_list_seq_gen")
    @SequenceGenerator(name = "black_list_seq_gen", sequenceName = "black_list_seq", allocationSize = 1)
    private Long id;
    @Column
    private String name;
}
