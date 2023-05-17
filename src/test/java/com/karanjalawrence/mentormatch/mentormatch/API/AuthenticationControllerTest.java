package com.karanjalawrence.mentormatch.mentormatch.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.karanjalawrence.mentormatch.mentormatch.Domain.AuthRequest;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Roles;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Services.AuthenticationService;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        User user = User.builder().email("test@example.com").password("password").role(Roles.MENTEE).build();
        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("result", "success");
        when(authenticationService.registerUser(user)).thenReturn(expectedResult);

        ResponseEntity<Map<String, Object>> responseEntity = authenticationController.createAccount(user);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());
        verify(authenticationService, times(1)).registerUser(user);
    }

    @Test
    void testLogin() {
        AuthRequest request = new AuthRequest("test@example.com", "password");
        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("result", "success");
        when(authenticationService.login(request)).thenReturn(expectedResult);

        ResponseEntity<Map<String, Object>> responseEntity = authenticationController.login(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResult, responseEntity.getBody());

        verify(authenticationService, times(1)).login(request);
    }
}
