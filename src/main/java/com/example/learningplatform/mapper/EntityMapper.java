package com.example.learningplatform.mapper;

import com.example.learningplatform.dto.CourseDto;
import com.example.learningplatform.dto.LessonDto;
import com.example.learningplatform.model.Course;
import com.example.learningplatform.model.Lesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    CourseDto toDto(Course course);

    LessonDto toDto(Lesson lesson);
}
