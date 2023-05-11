package com.karanjalawrence.mentormatch.mentormatch.API;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Implementations.UserDetailsImpl;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserDetailsController {
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/{id}/details")
    public ResponseEntity<UserDetails> createUserDetails(@PathVariable String id, @RequestBody UserDetails details) {
        User user = userRepository.getReferenceById(UUID.fromString(id));
        details.setUser(user);
        return ResponseEntity.ok().body(userDetailsService.createUserDetails(details));
    }

    @GetMapping("/details")
    public ResponseEntity<List<UserDetails>> getAlluserDetails() {
        return ResponseEntity.ok().body(userDetailsService.getAllUsers());
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Optional<UserDetails>> getuserDetailsById(@PathVariable String id) {
        return ResponseEntity.ok().body(userDetailsService.getUserDetailsById(UUID.fromString(id)));
    }

    @PatchMapping("/details/{id}")
    public ResponseEntity<UserDetails> updateUserDetailsById(@PathVariable String id,
            @RequestBody Map<String, Object> user) throws JsonMappingException {
        UserDetails existingUser = userDetailsService.getUserDetailsById(UUID.fromString(id)).get();
        var updatedDetails = objectMapper.updateValue(existingUser, user);

        return ResponseEntity.ok().body(userDetailsService.updateUserDetails(updatedDetails));
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deleteUserDetailsByUUID(@PathVariable String id) {
        try {
            userDetailsService.deleteUserDetailsByUUID(UUID.fromString(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to delete user due to: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The User Details were deleted Successfully");
    }

}
