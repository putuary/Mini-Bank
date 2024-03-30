package com.miniBank.controller;

import com.miniBank.model.entity.User;
import com.miniBank.model.request.AuthRequest;
import com.miniBank.model.request.LoginRequest;
import com.miniBank.model.response.CommonResponse;
import com.miniBank.model.response.LoginResponse;
import com.miniBank.service.AuthService;
import com.miniBank.utils.constant.ApiPathConstant;
import com.miniBank.utils.constant.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPathConstant.API + ApiPathConstant.VERSION + ApiPathConstant.AUTH)
public class AuthUserController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<User>> registerEmployee (@RequestBody AuthRequest authRequest) {
        User user = authService.registerEmployee(authRequest);
        String message =String.format(Constant.MESSAGE_SUCCESS_REGISTERED, user.getUsername());
        CommonResponse<User> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(user);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> loginUser (@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        LoginResponse result = authService.loginUser(loginRequest);
        String message =String.format(Constant.MESSAGE_SUCCESS_LOGIN, loginRequest.getUsername());
        CommonResponse<LoginResponse> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(result);
        response.setMessage(message);
        return ResponseEntity.status(response.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
