package com.karanjalawrence.mentormatch.mentormatch.API;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karanjalawrence.mentormatch.mentormatch.Domain.AuthRequest;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody User user) {
        return ResponseEntity.ok().body(authenticationService.registerUser(user));
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(authenticationService.login(request));
    }

}
