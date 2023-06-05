package com.karanjalawrence.mentormatch.mentormatch.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonMappingException;
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

    private List<User> usersSetup(){
        User first = new User();
        User second = new User();

        List<User> list = new ArrayList<>();
        list.add(second);
        list.add(first);

        return list;
    }

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBulkCreateUserAccounts() {
        when(userService.bulkCreateUsers(usersSetup())).thenReturn(usersSetup());
        ResponseEntity<List<User>> response  = userController.bulkCreateUserAccounts(usersSetup());

        verify(userService, times(1)).bulkCreateUsers(usersSetup());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersSetup(), response.getBody());
    }

    @Test
    void testCreateUserAccount() {
        User user = new User();

        when(userService.createUserAccount(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUserAccount(user);

        verify(userService, times(1)).createUserAccount(any(User.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

    }

    @Test
    void testDeleteUserByUUID() {
        UUID test = UUID.randomUUID();

        doNothing().when(userService).deleteUserByUUID(test);
        ResponseEntity<?> response = userController.deleteUserByUUID(test.toString());

        verify(userService, times(1)).deleteUserByUUID(test);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("The User was deleted Successfully", response.getBody());
    }

    @Test
    void testGetAllUserAccounts() {
        when(userService.getAllUsers()).thenReturn(usersSetup());
        ResponseEntity<List<User>> response = userController.getAllUserAccounts();

        verify(userService, times(1)).getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersSetup(), response.getBody());
    }

    @Test
    void testGetUserByEmail() {
        String testEmail = "test@email.com";

        User user = new User();

        Optional<User> userOptional = Optional.of(user);

        when(userService.getUserByEmail(testEmail)).thenReturn(userOptional);

        ResponseEntity<Optional<User>> response = userController.getUserByEmail(testEmail);

        verify(userService, times(1)).getUserByEmail(testEmail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userOptional, response.getBody());

    }

    @Test
    void testUpdateUserDetails() throws JsonMappingException {
        User existing = new User();
        User updated  =new User();

        UUID id = UUID.randomUUID();

        Map<String, Object> update = new HashMap<>();
        update.put("enabled", "true");

        when(userService.getUserByUUID(any(UUID.class))).thenReturn(Optional.of(existing));
        when(objectMapper.updateValue(any(User.class), any(Map.class))).thenReturn(updated);
        when(userService.updateUserDetails(any(User.class))).thenReturn(updated);

        ResponseEntity<User> response = userController.updateUserDetails(id.toString(), update);

        verify(userService, times(1)).getUserByUUID(any(UUID.class));
        verify(objectMapper, times(1)).updateValue(any(User.class), any(Map.class));
        verify(userService, times(1)).updateUserDetails(any(User.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());

    }
}
