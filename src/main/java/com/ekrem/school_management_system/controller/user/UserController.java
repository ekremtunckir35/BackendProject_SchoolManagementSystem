package com.ekrem.school_management_system.controller.user;

import com.ekrem.school_management_system.payload.request.user.UserRequest;
import com.ekrem.school_management_system.payload.request.user.UserRequestWithoutPassword;
import com.ekrem.school_management_system.payload.response.abstracts.BaseUserResponse;
import com.ekrem.school_management_system.payload.response.business.ResponseMessage;
import com.ekrem.school_management_system.payload.response.user.UserResponse;
import com.ekrem.school_management_system.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    //pageable yapılacak
    @GetMapping("/getUserByPage/{userRole}")
    public ResponseEntity<Page<UserResponse>>getUserByPage(
            @PathVariable String userRole,
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(value = "sort",defaultValue = "name")String sort,
            @RequestParam(value = "type",defaultValue = "desc")String type){

        Page<UserResponse>userResponses =userService.getUserByPage(
                page,size,sort,type,userRole);
        return ResponseEntity.ok(userResponses);

    }
//http://localhost:8090/user/getUserById/1
    @GetMapping("/getUserById/{userId}")
    public ResponseMessage<BaseUserResponse>getUserById(@PathVariable Long userId){
        return userService.findUserById(userId);
    }

    //http://localhost:8090/user/deleteUserById/1
    @DeleteMapping("/deleteUserById/{userId}")
    public ResponseEntity<String>deleteUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.deleteUserById(userId));

    }
    //http://localhost:8090/user/update/1
    @PostMapping("/update/{userId}")
    public ResponseMessage<UserResponse>updateByUserId(
            @RequestBody @ Valid UserRequest userRequest,
            @PathVariable Long userId){
        return userService.updateByUserId(userRequest,userId);
    }

    //http://localhost:8090/user/updateLoggedInUser
    @PatchMapping("/updateLoggedInUser")
    public ResponseEntity<String>updateLoggedInUser(
            @RequestBody @Valid UserRequestWithoutPassword userRequestWithoutPassword,
        HttpServletRequest httpServletRequest){
    return ResponseEntity.ok(userService.updateLoggedInUser(
            userRequestWithoutPassword,httpServletRequest));
        }

    }



