package com.ekrem.school_management_system.payload.response.business;

import com.ekrem.school_management_system.entity.concretes.businnes.EducationTerm;
import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import com.ekrem.school_management_system.entity.enums.Day;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonProgramResponse {


    private Long lessonProgramId;

    private Day day;

    private LocalTime startTime;

    private LocalTime stopTime;

    private List<Lesson> lessonName;

    private EducationTerm educationTerm;
}
