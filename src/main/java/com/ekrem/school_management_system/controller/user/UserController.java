package com.ekrem.school_management_system.controller.user;

import com.ekrem.school_management_system.payload.request.user.UserRequest;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.payload.response.user.UserResponse;
import com.ekrem.school_management_system.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("save/{userRole}")
    public ResponseEntity<ResponseMessage<UserResponse>>saveUser(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable String userRole) {
        return ResponseEntity.ok(userService.saveUser(userRequest, userRole));
    }
}
