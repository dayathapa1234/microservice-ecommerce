package com.ecommerce.account.controller;

import com.ecommerce.account.entity.User;
import com.ecommerce.account.enums.USER_ROLE;
import com.ecommerce.account.response.AuthResponse;
import com.ecommerce.account.response.SignupRequest;
import com.ecommerce.account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest request){

        String jwt = authService.createUSer(request);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("registration successful");
        response.setRole(USER_ROLE.ROLE_CUSTOMER);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
