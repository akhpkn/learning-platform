package com.example.learningplatform.repository;

import com.example.learningplatform.model.Employee;
import com.example.learningplatform.model.EmployeeProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeProgressRepository extends JpaRepository<EmployeeProgress, UUID> {

    Optional<EmployeeProgress> findByEmployee(Employee employee);

    @Query(
        value = "select ep from EmployeeProgress ep join fetch ep.employee",
        countQuery = "select count (ep) from EmployeeProgress ep"
    )
    Page<EmployeeProgress> findWithEmployee(Pageable pageable);
}
