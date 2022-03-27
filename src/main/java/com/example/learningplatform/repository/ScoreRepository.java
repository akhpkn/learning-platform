package com.example.learningplatform.repository;

import com.example.learningplatform.model.Role;
import com.example.learningplatform.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
