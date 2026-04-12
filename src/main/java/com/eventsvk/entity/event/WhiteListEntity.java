package com.eventsvk.entity.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "white_list")
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhiteListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_seq_gen")
    @SequenceGenerator(name = "user_role_seq_gen", sequenceName = "user_role_seq", allocationSize = 1)
    private Long id;
    @Column
    private String name;
}
