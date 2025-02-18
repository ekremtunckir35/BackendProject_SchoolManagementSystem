package com.ekrem.school_management_system.service.user;

import com.ekrem.school_management_system.entity.concretes.user.User;
import com.ekrem.school_management_system.payload.mappers.UserMapper;
import com.ekrem.school_management_system.payload.messages.SuccessMessages;
import com.ekrem.school_management_system.payload.request.user.UserRequest;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.payload.response.user.UserResponse;
import com.ekrem.school_management_system.repository.user.UserRepository;
import com.ekrem.school_management_system.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UniquePropertyValidator uniquePropertyValidator;;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
        //validate unique properties

        uniquePropertyValidator.checkDuplication(
                userRequest.getUsername(),
                userRequest.getSsn(),
                userRequest.getPhoneNumber(),
                userRequest.getEmail()

        );
        //DTO-->entity mapping

        User userToSave = userMapper.mapUserRequestToUser(userRequest, userRole);

        //save operation
        User savedUser = userRepository.save(userToSave);

        //entity -->DTO mapping

        return ResponseMessage.<UserResponse>builder()
                .message(SuccessMessages.USER_CREATE)
                .returnBody(userMapper.mapUserToUserResponse(savedUser))
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
