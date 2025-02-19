package com.ekrem.school_management_system.payload.request.user;

import com.ekrem.school_management_system.payload.request.abstracts.AbstractUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserRequestWithoutPassword extends AbstractUserRequest {
}
