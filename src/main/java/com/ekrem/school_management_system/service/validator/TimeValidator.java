package com.ekrem.school_management_system.service.validator;

import com.ekrem.school_management_system.exception.BadRequestException;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeValidator {

    public void checkStartTimeIsBefore (LocalTime start, LocalTime stop) {
        if (start.isBefore(stop) || start.equals(stop)) {
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
}
