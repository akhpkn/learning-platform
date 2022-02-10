package com.example.learningplatform.repository;

import com.example.learningplatform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Role.RoleName> {

    Role findByName(Role.RoleName name);
}
