package com.ekrem.school_management_system.payload.request.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserRequest  extends  AbstractUserRequest{

    @NotNull(message = "Please enter your username")
    @Size(min = 8, max = 16,message = "Your username should be at least 4 chars")
    private String password;

    private Boolean buildIn =false;
}
