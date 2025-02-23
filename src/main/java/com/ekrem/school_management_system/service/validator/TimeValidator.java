package com.ekrem.school_management_system.service.validator;

import com.ekrem.school_management_system.exception.BadRequestException;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import java.time.LocalTime;


import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class TimeValidator {


    //validate if start time is before stop time
    public void checkStartIsBeforeStop(LocalTime start, LocalTime stop) {
        if (start.isAfter(stop) || start.equals(stop)) {
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }

    }

    public void checkStartIsBeforeStart(@NotNull(message = "Please enter a start time") LocalTime startTime,
                                        @NotNull(message = "Please enter a stop time") LocalTime stopTime) {

    }
}