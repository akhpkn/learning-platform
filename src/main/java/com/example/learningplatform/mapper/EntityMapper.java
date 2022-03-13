package com.example.learningplatform.mapper;

import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.dto.EmployeeRateDto;
import com.example.learningplatform.dto.LessonDto;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.EmployeeProgress;
import com.example.learningplatform.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    CourseDto toDto(Course course);

    LessonDto toDto(Lesson lesson);

    @Mappings(
        value = {
            @Mapping(target = "employeeId", source = "progress.employee.id"),
            @Mapping(target = "email", source = "progress.employee.email"),
            @Mapping(target = "name", source = "progress.employee.name"),
            @Mapping(target = "surname", source = "progress.employee.surname")
        }
    )
    EmployeeRateDto toRateDto(EmployeeProgress progress);

    void updateCourseFromDto(CourseDto dto, @MappingTarget Course entity);
}
