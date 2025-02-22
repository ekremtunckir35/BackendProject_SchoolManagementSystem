package com.ekrem.school_management_system.payload.mappers;

import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import com.ekrem.school_management_system.payload.request.business.LessonRequest;
import com.ekrem.school_management_system.payload.response.business.LessonResponse;
import org.springframework.stereotype.Component;


@Component
public class LessonMapper {

    public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest){
        return Lesson.builder()
                .lessonName(lessonRequest.getLessonName())
                .creditScore(lessonRequest.getCreditScore())
                .isCompulsory(lessonRequest.getIsCompulsory())
                .build();
    }

    public LessonResponse mapLessonToLessonResponse(Lesson lesson){
        return LessonResponse.builder()
                .lessonId(lesson.getId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }
}
