package com.portfolio.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.tracker.dto.request.UserRequestDto;
import com.portfolio.tracker.dto.response.CommonResponse;
import com.portfolio.tracker.dto.response.UserResponse;
import com.portfolio.tracker.service.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signup(@RequestBody UserRequestDto userRequestDto) {

        try {
            UserResponse user = userService.createUser(userRequestDto);
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("User created successfully")
                    .data(user)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message(e.getMessage())
                    .build());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponse user = userService.signin(userRequestDto);
            CommonResponse response = CommonResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("User signed in successfully")
                    .data(user)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message(e.getMessage())
                    .build());
        }
    }

}
