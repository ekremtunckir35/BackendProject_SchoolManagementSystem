package com.ekrem.school_management_system.service.validator;

import com.ekrem.school_management_system.entity.concretes.user.User;
import com.ekrem.school_management_system.exception.ConflictException;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import com.ekrem.school_management_system.payload.request.abstracts.AbstractUserRequest;
import com.ekrem.school_management_system.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkUniqueProperty(User user, AbstractUserRequest userRequest){
        String updatedUsername = "";
        String updatedEmail = "";
        String updatedPhone = "";
        String updatedSsn = "";
        boolean isChanged = false;

        //we're checking if the updated username is different from the current username

        if(!user.getUsername().equals(userRequest.getUsername())){
            updatedUsername = userRequest.getUsername();
            isChanged = true;
        }
        //we're checking if the updated email is different from the current email
        if(!user.getEmail().equals(userRequest.getEmail())){
            updatedEmail = userRequest.getEmail();
            isChanged = true;
        }
        //we're checking if the updated phone number is different from the current phone number
        if(!user.getPhoneNumber().equals(userRequest.getPhoneNumber())){
            updatedPhone = userRequest.getPhoneNumber();
            isChanged = true;
        }
        //we're checking if the updated ssn is different from the current ssn
        if(!user.getSsn().equals(userRequest.getSsn())){
            updatedSsn = userRequest.getSsn();
            isChanged = true;
        }
        if(isChanged){
            checkDuplication(updatedUsername, updatedSsn, updatedPhone, updatedEmail);
        }
    }



    public void checkDuplication(
            String username,
            String ssn,
            String phone,
            String email) {
        if(userRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }
        if(userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }
        if(userRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
        if(userRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        }

    }

}