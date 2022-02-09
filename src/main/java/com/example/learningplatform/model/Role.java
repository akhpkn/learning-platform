package com.example.learningplatform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @Enumerated(value = EnumType.STRING)
    private RoleName name;

    public enum RoleName {
        ROLE_EMPLOYEE,
        ROLE_ADMIN
    }
}
