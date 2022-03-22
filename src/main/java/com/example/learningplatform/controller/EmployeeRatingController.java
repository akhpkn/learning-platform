package com.example.learningplatform.controller;

import com.example.learningplatform.dto.EmployeeRateDto;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.model.EmployeeProgress;
import com.example.learningplatform.service.EmployeeRatingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee-rating")
@AllArgsConstructor
public class EmployeeRatingController {

    private final EmployeeRatingService service;
    private final EntityMapper mapper;

    @GetMapping
    public List<EmployeeRateDto> getTop10EmployeesByCoursesCompleted() {
        List<EmployeeProgress> list = service.getTop10ByCoursesCompleted();

        return list.stream()
            .map(mapper::toRateDto)
            .collect(Collectors.toList());
    }
}
