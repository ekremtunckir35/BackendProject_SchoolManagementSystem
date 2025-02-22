package com.ekrem.school_management_system.service.businnes;

import com.ekrem.school_management_system.entity.concretes.businnes.Lesson;
import com.ekrem.school_management_system.entity.exception.ConflictException;
import com.ekrem.school_management_system.entity.exception.ResourceNotFoundException;
import com.ekrem.school_management_system.exception.ConflictException;
import com.ekrem.school_management_system.payload.mappers.LessonMapper;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import com.ekrem.school_management_system.payload.messages.SuccessMessages;
import com.ekrem.school_management_system.payload.request.business.LessonRequest;
import com.ekrem.school_management_system.payload.response.business.LessonResponse;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.repository.businnes.LessonRepository;
import com.ekrem.school_management_system.service.helper.PageableHelper;
import com.ekrem.school_management_system.util.PageableHelper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {
        //validate - lesson name must be unique
        isLessonExistByName(lessonRequest.getLessonName());
        //map DTO to Entity
        Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
        Lesson savedLesson = lessonRepository.save(lesson);

        return ResponseMessage.<LessonResponse>builder()
                .returnBody(lessonMapper.mapLessonToLessonResponse(savedLesson))
                .httpStatus(HttpStatus.CREATED)
                .message(SuccessMessages.LESSON_SAVE)
                .build();
    }

    public ResponseMessage<LessonResponse> deleteLesson(Long lessonId) {

        return ResponseMessage.<LessonResponse>builder()
                .returnBody(lessonMapper.mapLessonToLessonResponse(deleteLessonById(lessonId)))
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.LESSON_DELETE)
                .build();
    }

    public ResponseMessage<LessonResponse> findLessonByName(String lessonName) {
        return ResponseMessage.<LessonResponse>builder()
                .message(SuccessMessages.LESSON_FOUND)
                .returnBody(lessonMapper.mapLessonToLessonResponse(getLessonByName(lessonName)))
                .httpStatus(HttpStatus.OK)
                .build();
    }


    private void isLessonExistByName(String lessonName) {
        if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {
            throw new com.ekrem.school_management_system.exception.ConflictException(
                    String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE, lessonName));
        }
    }

    private Lesson getLessonByName(String lessonName) {
        return lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.NOT_FOUND_LESSON_IN_LIST, lessonName)));
    }


    public Page<LessonResponse> getLessonByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
        Page<Lesson> lessons = lessonRepository.findAll(pageable);
        return lessons.map(lessonMapper::mapLessonToLessonResponse);
    }

    private Lesson deleteLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonId)));
        lessonRepository.delete(lesson);
        return lesson;
    }

    public Lesson isLessonExistById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonId)));
    }


    public LessonResponse updateLesson(@Valid LessonRequest lessonRequest, Long lessonId) {
        //validate if exist
        Lesson lessonFromDb = isLessonExistById(lessonId);
        //check if user changes the name
        if(!lessonRequest.getLessonName().equals(lessonFromDb.getLessonName())) {
            //then check DB in case of conflict
            isLessonExistByName(lessonRequest.getLessonName());
        }
        Lesson lessonToUpdate = lessonMapper.mapLessonRequestToLesson(lessonRequest);
        lessonToUpdate.setId(lessonId);
        Lesson savedLesson = lessonRepository.save(lessonToUpdate);
        return lessonMapper.mapLessonToLessonResponse(savedLesson);
    }

    public List<Lesson> getAllByIdSet(List<Long> idSet) {
        return idSet.stream()
                .map(this::isLessonExistById)
                .collect(Collectors.toList());
    }
}