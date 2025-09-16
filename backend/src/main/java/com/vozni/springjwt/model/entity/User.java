package com.vozni.springjwt.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "default_seq")
    long id;
    @Column(columnDefinition = "CHARACTER VARYING (32)")
    @NotNull
    @NotBlank()
    @Size(min=4, max=64)
    String username;
    // this is like json ignore but only on read, password will be got in api gateway but not sent to user
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(columnDefinition = "CHARACTER VARYING (128)")
    @NotNull
    @NotBlank()
    @Size(min=4, max = 128)
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roleSet;
}
