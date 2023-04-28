package com.karanjalawrence.mentormatch.mentormatch.API;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    public final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<User> createUserAccount(@RequestBody User user){
        return ResponseEntity.ok().body(userService.createUserAccount(user));
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUserAccounts(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@RequestBody String email){
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @PatchMapping("{uuid}")
    public ResponseEntity<User> updateUserDetails(@PathVariable String uuid, @RequestBody Map<String, Object> details)throws JsonMappingException{
        User user = userService.getUserByUUID(UUID.fromString(uuid)).get();

        var updatedDetails = objectMapper.updateValue(user, details);

        return ResponseEntity.ok().body(userService.updateUserDetails(updatedDetails));
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<?> deleteUserByUUID(@PathVariable String uuid){
        try{
            userService.deleteUserByUUID(UUID.fromString(uuid));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user due to: "+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The User was deleted Successfully");
    }
}
