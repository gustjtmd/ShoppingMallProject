package com.example.gustjtmdshop.api.controller;

import com.example.gustjtmdshop.api.request.UserDto;
import com.example.gustjtmdshop.api.service.AuthService;
import com.example.gustjtmdshop.api.service.KeycloakUserService;
import com.sun.net.httpserver.Authenticator.Result;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private KeycloakUserService keycloakUserService;




    /*
     * 회원가입
     * */
    @PostMapping("/signup")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        if(authService.existsByUsername(userDto.getUserName())) {
            return ResponseEntity.ok("유저가 존재합니다.");
        }

        UserDto result = authService.createUser(userDto);
        return ResponseEntity.ok(result);
    }

    /*
     *  로그인
     * */
    @PostMapping(path = "/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody  UserDto userDto) {

        AccessTokenResponse response = authService.setAuth(userDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public List<UserRepresentation> getAllUsers() {
        return keycloakUserService.getAllUsers();
    }

}
