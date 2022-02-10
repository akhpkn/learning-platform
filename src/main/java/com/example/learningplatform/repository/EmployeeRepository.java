package com.example.learningplatform.repository;

import com.example.learningplatform.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e join fetch e.roles where e.email=:email")
    Optional<Employee> findByEmailWithRoles(@Param("email") String email);

    @Query("select e from Employee e join fetch e.roles where e.id=:id")
    Optional<Employee> findByIdWithRoles(@Param("id") UUID id);

    Boolean existsByEmail(String email);
}
