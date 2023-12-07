package com.example.gustjtmdshop.api.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String email;

    @NotNull
    private UserRole userRole;

    private int status;

    private String statusInfo;

}
