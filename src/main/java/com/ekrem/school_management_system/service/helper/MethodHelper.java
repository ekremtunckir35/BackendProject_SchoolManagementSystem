package com.ekrem.school_management_system.service.helper;

import com.ekrem.school_management_system.entity.concretes.user.User;
import com.ekrem.school_management_system.exception.BadRequestException;
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

    public void checkBuildIn(User user){
        if(user.getBuildIn()){
            throw  new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }

    public User loadByUsername(String username){
        User user =userRepository.findByUsername(username);
        if(user ==null){
            throw  new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,username));
        }
        return user;
    }
}
