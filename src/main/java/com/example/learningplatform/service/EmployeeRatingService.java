package com.example.learningplatform.service;

import com.example.learningplatform.model.EmployeeProgress;
import com.example.learningplatform.repository.EmployeeProgressRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeRatingService {

    private final EmployeeProgressRepository employeeProgressRepository;

    public List<EmployeeProgress> getTop10ByCoursesCompleted() {
        Pageable pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "coursesCompleted"));
        Page<EmployeeProgress> page = employeeProgressRepository.findWithEmployee(pageRequest);

        return page.getContent();
    }
}
