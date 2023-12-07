package com.example.gustjtmdshop.api.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

    private String email;

    private String password;

    @NotNull
    private UserRole userRole;

    private int status;

    private String statusInfo;

}
