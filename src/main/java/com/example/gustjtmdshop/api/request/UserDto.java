package com.example.gustjtmdshop.api.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

    private String userName;
    private String password;
    private String email;

    @NotNull
    private UserRole userRole;

    private List<String> groups;

    private int status;

    private String statusInfo;

}
