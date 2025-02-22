package com.ekrem.school_management_system.controller.businnes;

import com.ekrem.school_management_system.payload.request.business.LessonProgramRequest;
import com.ekrem.school_management_system.payload.response.business.LessonProgramResponse;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.service.businnes.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {

    private final LessonProgramService lessonProgramService;

    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
    @PostMapping  ("/save")
    public ResponseMessage<LessonProgramResponse>saveLessonProgram(
        @RequestBody @Valid LessonProgramRequest lessonProgramRequest) {
        return lessonProgramService.saveLessonProgram(lessonProgramRequest);

    }

}
