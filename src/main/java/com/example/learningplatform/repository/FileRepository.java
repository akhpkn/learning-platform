package com.example.learningplatform.repository;

import com.example.learningplatform.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

    File findByFileId(Long fileId);
}