package com.ekrem.school_management_system.service.businnes;

import com.ekrem.school_management_system.entity.concretes.businnes.EducationTerm;
import com.ekrem.school_management_system.exception.BadRequestException;
import com.ekrem.school_management_system.exception.ConflictException;
import com.ekrem.school_management_system.exception.ResourceNotFoundException;
import com.ekrem.school_management_system.payload.mappers.EducationTermMapper;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import com.ekrem.school_management_system.payload.messages.SuccessMessages;
import com.ekrem.school_management_system.payload.request.business.EducationTermRequest;
import com.ekrem.school_management_system.payload.response.business.EducationTermResponse;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.repository.businnes.EducationTermRepository;
import com.ekrem.school_management_system.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class EducationTermService {

    private final EducationTermRepository educationTermRepository;
    private final EducationTermMapper educationTermMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<EducationTermResponse> save(
            @Valid EducationTermRequest educationTermRequest) {
        //validation
        validateEducationTermDates(educationTermRequest);
        //write mappers DTO->Entity + Entity->DTO
        EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        EducationTerm savedEducationTerm = educationTermRepository.save(educationTerm);
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_SAVE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    private void validateEducationTermDates(EducationTermRequest educationTermRequest) {
        //validate request by reg/start/stop
        validateEducationTermDatesForRequest(educationTermRequest);
        //only one education term can exist in a year
        if(educationTermRepository.existsByTermAndYear(
                educationTermRequest.getTerm(),
                educationTermRequest.getStartDate().getYear())){
            throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }
        //validate not to have any conflict with other education terms
        educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
                .forEach(educationTerm -> {
                    if(!educationTerm.getStartDate().isAfter(educationTermRequest.getEndDate())
                            || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate())){
                        throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
                    }
                });
    }


    private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest) {
        //reg<start
        if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
            throw new ConflictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        //end>start
        if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new ConflictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }

    public ResponseMessage<EducationTermResponse> updateEducationTerm(
            @Valid EducationTermRequest educationTermRequest, Long educationTermId) {


        //check if education term exist
        isEducationTermExist(educationTermId);

        //validate dates
        validateEducationTermDatesForRequest(educationTermRequest);

        //mapping
        EducationTerm term = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
        term.setId(educationTermId);//setId unutma ,yazmazsan yeni bir data olusturursun.


        //return by mapping it to DTO
        return ResponseMessage.<EducationTermResponse>builder()
                .message(SuccessMessages.EDUCATION_TERM_UPDATE)
                .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(educationTermRepository.save(term)))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public EducationTerm isEducationTermExist(Long educationTermId) {
        return educationTermRepository.findById(educationTermId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE));
    }

    public EducationTermResponse getEducationTermById(Long educationTermId) {
        // Validate if the education term exists in the database
        EducationTerm educationTerm = isEducationTermExist(educationTermId);
        // Map the entity to DTO and return the response
        return educationTermMapper.mapEducationTermToEducationTermResponse(educationTerm);
    }

    public Page<EducationTermResponse> getByPage(int page, int size, String sort, String type) {
        Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
        //fetch paginated and sorted data from DB
        Page<EducationTerm>educationTerms = educationTermRepository.findAll(pageable);
        //use mapper
        return educationTerms.map(educationTermMapper::mapEducationTermToEducationTermResponse);
    }

    public ResponseMessage deleteById(Long educationTermId) {
        isEducationTermExist(educationTermId);
        educationTermRepository.deleteById(educationTermId);
        return ResponseMessage.builder()
                .message(SuccessMessages.EDUCATION_TERM_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public List<EducationTermResponse> getAllEducationTerms() {
        List<EducationTerm> allEducationTerms = educationTermRepository.findAll();
        return allEducationTerms.stream().map(educationTermMapper::mapEducationTermToEducationTermResponse).collect(Collectors.toList());
    }

    public List<EducationTermResponse> getAllEducationTerm() {
        return educationTermRepository.findAll().stream()
                .map(educationTermMapper::mapEducationTermToEducationTermResponse)
                .collect(Collectors.toList());
    }
}