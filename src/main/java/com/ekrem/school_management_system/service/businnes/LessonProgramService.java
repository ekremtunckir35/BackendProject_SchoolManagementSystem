package com.ekrem.school_management_system.service.businnes;


import com.ekrem.school_management_system.entity.concretes.businnes.EducationTerm;
import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import com.ekrem.school_management_system.entity.concretes.businnes.LessonProgram;
import com.ekrem.school_management_system.payload.mappers.LessonProgramMapper;
import com.ekrem.school_management_system.payload.messages.SuccessMessages;
import com.ekrem.school_management_system.payload.request.business.LessonProgramRequest;
import com.ekrem.school_management_system.payload.response.business.LessonProgramResponse;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.repository.businnes.LessonProgramRepository;
import com.ekrem.school_management_system.service.validator.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import java.util.List;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LessonProgramService {



        private final LessonProgramRepository lessonProgramRepository;
        private final LessonService lessonService;
        private final EducationTermService educationTermService;
        private final TimeValidator timeValidator;
        private final LessonProgramMapper lessonProgramMapper;

        public ResponseMessage<LessonProgramResponse> saveLessonProgram(
                @Valid LessonProgramRequest lessonProgramRequest) {

            //get lesson from DB
            List<Lesson> lessons = lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());

            //get education term from DB

            EducationTerm educationTerm = educationTermService.isEducationTermExist(
                    lessonProgramRequest.getEducationTermId());

            //vallidate start + end time

            timeValidator.checkStartIsBeforeStart(lessonProgramRequest.getStartTime(),
                    lessonProgramRequest.getStopTime());


            //map request to entity

            LessonProgram lessonProgramToSave = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(
                    lessonProgramRequest, lessons, educationTerm);

            LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgramToSave);

            return ResponseMessage.<LessonProgramResponse>builder()
                    .returnBody(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
                    .httpStatus(HttpStatus.CREATED)
                    .message(SuccessMessages.LESSON_PROGRAM_SAVE)
                    .build();

        }

    public List<LessonProgramResponse> getAllLessonPrograms() {
        List<LessonProgram> lessonPrograms = lessonProgramRepository.findAll();
        return lessonPrograms.stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .toList();
    }

    public LessonProgramResponse getLessonProgramById(Long id) {
        LessonProgram lessonProgram = lessonProgramRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson Program not found"));
        return lessonProgramMapper.mapLessonProgramToLessonProgramResponse(lessonProgram);
    }

    public List<LessonProgramResponse> getAllUnassigned() {
        return lessonProgramRepository.findByUsers_IdNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public List<LessonProgramResponse> getAllAssigned() {
        return lessonProgramRepository.findByUsers_IdNotNull()
                .stream()
                .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
                .collect(Collectors.toList());
    }

    public ResponseMessage deleteLessonProgramById(Long id) {
        lessonProgramRepository.deleteById(id);
        return ResponseMessage.builder()
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.LESSON_PROGRAM_DELETE)
                .build();
    }
}

