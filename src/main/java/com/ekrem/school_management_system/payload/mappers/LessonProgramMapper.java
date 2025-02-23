package com.ekrem.school_management_system.payload.mappers;

import com.ekrem.school_management_system.entity.concretes.businnes.EducationTerm;
import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import com.ekrem.school_management_system.entity.concretes.businnes.LessonProgram;
import com.ekrem.school_management_system.payload.request.business.LessonProgramRequest;
import com.ekrem.school_management_system.payload.response.business.LessonProgramResponse;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LessonProgramMapper {

    public LessonProgram mapLessonProgramRequestToLessonProgram(
            LessonProgramRequest lessonProgramRequest,
            List<Lesson> lessonSet,
            EducationTerm educationTerm) {
        return LessonProgram.builder()
                .day(lessonProgramRequest.getDay())
                .startTime(lessonProgramRequest.getStartTime())
                .stopTime(lessonProgramRequest.getStopTime())
                .lessons(lessonSet)
                .educationTerm(educationTerm)
                .build();


    }

    public LessonProgramResponse mapLessonProgramToLessonProgramResponse(LessonProgram lessonProgram) {
        return LessonProgramResponse.builder()
                .lessonProgramId(lessonProgram.getId())
                .lessonName(lessonProgram.getLessons())
                .day(lessonProgram.getDay())
                .startTime(lessonProgram.getStartTime())
                .stopTime(lessonProgram.getStopTime())
                .educationTerm(lessonProgram.getEducationTerm())
                .build();


    }
}
