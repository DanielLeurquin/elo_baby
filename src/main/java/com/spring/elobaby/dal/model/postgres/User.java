package com.spring.elobaby.dal.model.postgres;

import com.spring.elobaby.dal.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class User {

    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    Role role;


}
