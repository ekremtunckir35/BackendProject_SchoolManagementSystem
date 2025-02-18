package com.ekrem.school_management_system.service.user;

import com.ekrem.school_management_system.entity.concretes.user.UserRole;
import com.ekrem.school_management_system.entity.enums.RoleType;
import com.ekrem.school_management_system.exception.ResourceNotFoundException;
import com.ekrem.school_management_system.payload.messages.ErrorMessages;
import com.ekrem.school_management_system.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType){
        return userRoleRepository.findByUserRoleType(roleType)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
    }


    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }


}
