package com.ekrem.school_management_system.service.helper;

import com.ekrem.school_management_system.entity.concretes.user.User;
import com.ekrem.school_management_system.exception.ResourceNotFoundException;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import com.ekrem.school_management_system.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;

    public User isUserExist(Long Id){
        return userRepository.findById(Id).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, Id)
                ));
    }
}
