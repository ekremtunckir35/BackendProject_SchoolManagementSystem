package com.ekrem.school_management_system.service.businnes;

import com.ekrem.school_management_system.entity.concretes.businnes.EducationTerm;
import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import com.ekrem.school_management_system.payload.request.business.LessonProgramRequest;
import com.ekrem.school_management_system.payload.response.business.LessonProgramResponse;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.repository.businnes.LessonProgramRepository;
import com.ekrem.school_management_system.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;
    private final LessonService lessonService;
    private final EducationTermService educationTermService;
    private  final TimeValidator timeValidator;

    public ResponseMessage<LessonProgramResponse> saveLessonProgram(
            @Valid LessonProgramRequest lessonProgramRequest) {


        //get lesson from DB

        Set<Lesson>lesson = lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());

        //get education term from DB

        EducationTerm educationTerm = educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());

        //validate start + end time

        timeValidator.checkStartIsBeforeStop(lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());

        //mapping

    }
}
