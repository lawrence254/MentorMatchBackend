package com.karanjalawrence.mentormatch.mentormatch.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserService;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBulkCreateUserAccounts() {

    }

    @Test
    void testCreateUserAccount() {
        UUID id = UUID.randomUUID();
        User user = new User();

        when(userService.createUserAccount(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUserAccount(user);

        verify(userService, times(1)).createUserAccount(any(User.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

    }

    @Test
    void testDeleteUserByUUID() {

    }

    @Test
    void testGetAllUserAccounts() {

    }

    @Test
    void testGetUserByEmail() {

    }

    @Test
    void testUpdateUserDetails() {

    }
}
