package com.ekrem.school_management_system.payload.response.user;

import com.ekrem.school_management_system.payload.response.abstracts.BaseUserResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class UserResponse  extends BaseUserResponse {
}
