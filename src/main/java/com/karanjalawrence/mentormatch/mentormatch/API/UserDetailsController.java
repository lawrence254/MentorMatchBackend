package com.karanjalawrence.mentormatch.mentormatch.API;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Implementations.UserDetailsImpl;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserDetailsController {
    private final UserDetailsImpl userDetailsImpl;
    private final UserRepository userRepository;

    @PostMapping("/{id}/details")
    public ResponseEntity<UserDetails> createUserDetails(@PathVariable String id, @RequestBody UserDetails details){
        User user = userRepository.getReferenceById(UUID.fromString(id));
        details.setUser(user);
        return ResponseEntity.ok().body(userDetailsImpl.createUserDetails(details));
    }

}
