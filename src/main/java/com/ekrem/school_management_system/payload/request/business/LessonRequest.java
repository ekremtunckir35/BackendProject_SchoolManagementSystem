package com.ekrem.school_management_system.payload.request.business;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonRequest {

    @NotNull(message = "Please enter Lesson name")
    @Size(min = 2, max = 16, message = "Lesson name must be between 2 and 16 characters")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Lesson name must contain only letters")
    private String lessonName;

    @NotNull(message = "Please enter Lesson credit score")
    private Integer creditScore;


    @NotNull(message = "Please enter is compulsory")
    private  Boolean isCompulsory;



}
