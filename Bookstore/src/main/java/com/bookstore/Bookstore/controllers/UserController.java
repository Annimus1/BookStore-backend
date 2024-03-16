package com.bookstore.Bookstore.controllers;

import com.bookstore.Bookstore.domains.dto.JwtResponseDto;
import com.bookstore.Bookstore.domains.dto.UserRequestDto;
import com.bookstore.Bookstore.domains.models.UserEntity;
import com.bookstore.Bookstore.services.AuthService;
import com.bookstore.Bookstore.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
    
    @PostMapping(path = "register")
    public ResponseEntity<UserEntity> save(@RequestBody UserRequestDto customerDtoNew) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(new UserEntity(customerDtoNew)));
    }

    @PostMapping(path = "login")
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody UserRequestDto authCustomerDto) {
        return ResponseEntity.ok(authService.signIn(authCustomerDto));
    }

    
    @PostMapping(path = "sing-out")
    public ResponseEntity<JwtResponseDto> signOut(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(authService.signOut(jwt));
    }

}
