package com.ekrem.school_management_system.payload.request.business;

import com.ekrem.school_management_system.entity.enums.Day;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class LessonProgramRequest {

    @NotNull(message = "Please enter a day")
    private Day day;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "US")
    @NotNull(message = "Please enter a start time")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm",timezone = "US")
    @NotNull(message = "Please enter a stop time")
    private LocalTime stopTime;

    @NotNull(message = "Please select lesson")
    @Size(min = 1, message = "Please select at least one lesson")
    private List<Long> lessonIdList;

    @NotNull(message = "Please select a term")
    private Long educationTermId;


}
