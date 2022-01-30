package com.example.learningplatform.controller;

import com.example.learningplatform.controller.model.LessonCreationRequest;
import com.example.learningplatform.controller.model.UploadFileResponse;
import com.example.learningplatform.dto.LessonDto;
import com.example.learningplatform.mapper.EntityMapper;
import com.example.learningplatform.model.Lesson;
import com.example.learningplatform.model.Video;
import com.example.learningplatform.service.LessonService;
import com.example.learningplatform.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lessons")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final EntityMapper mapper;
    private final VideoService videoService;

    @GetMapping
    public List<LessonDto> getAllByCourseId(@RequestParam("courseId") Long courseId) {
        return lessonService.getAllByCourseId(courseId).stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @PostMapping
    public LessonDto createLesson(@RequestBody LessonCreationRequest request) {
        Lesson lesson = lessonService.createLesson(request.getTitle(), request.getCourseId());
        return mapper.toDto(lesson);
    }

    @PostMapping("{lessonId}/video")
    public UploadFileResponse addVideoToLesson(@PathVariable("lessonId") Long lessonId,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        Lesson lesson = lessonService.getLessonById(lessonId);
        if (lesson.getVideo() != null)
            videoService.deleteVideo(lesson.getVideo());

        Video video = videoService.store(file);
        lesson.setVideo(video);
        lessonService.saveLesson(lesson);

        return new UploadFileResponse(video.getUrl(), video.getType(), file.getSize());
    }

//    @PostMapping("{lessonId}/videourl")
//    public addVideoUrl(@PathVariable("lessonId") Long lessonId,
//                                         @RequestParam("url") String url) {
//        Lesson lesson = lessonRepository.findByLessonId(lessonId);
//        Video video = videoService.saveUrl(url);
//
//        lesson.setVideo(video);
//        lessonRepository.save(lesson);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
