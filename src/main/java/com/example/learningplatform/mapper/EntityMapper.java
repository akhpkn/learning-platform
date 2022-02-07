package com.example.learningplatform.mapper;

import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.dto.LessonDto;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    CourseDto toDto(Course course);

    LessonDto toDto(Lesson lesson);

    void updateCourseFromDto(CourseDto dto, @MappingTarget Course entity);
}
