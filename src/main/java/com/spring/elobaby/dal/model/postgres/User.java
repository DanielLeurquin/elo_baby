package com.spring.elobaby.dal.model.postgres;

import com.spring.elobaby.dal.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;

    Integer elo;

    @Enumerated(EnumType.STRING)
    Role role;


}
