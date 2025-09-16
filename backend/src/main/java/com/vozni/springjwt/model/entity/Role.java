package com.vozni.springjwt.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "default_seq")
    long id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "CHARACTER VARYING(8)")
    Roles role;

}
