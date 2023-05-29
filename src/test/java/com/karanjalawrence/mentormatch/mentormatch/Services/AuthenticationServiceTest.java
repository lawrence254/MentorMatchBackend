package com.karanjalawrence.mentormatch.mentormatch.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.karanjalawrence.mentormatch.mentormatch.Configs.JWTConfig;
import com.karanjalawrence.mentormatch.mentormatch.Domain.AuthRequest;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Roles;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Token;
import com.karanjalawrence.mentormatch.mentormatch.Domain.TokenType;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.TokenRepository;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTConfig jwtConfig;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role(Roles.MENTEE)
                .build();

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .password("encodedPassword")
                .role(Roles.MENTEE)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtConfig.generateToken(any(User.class))).thenReturn("jwtToken");

        
        Map<String, Object> result = authenticationService.registerUser(user);

        assertNotNull(result);
        assertTrue(result.containsKey("result"));
        Map<String, Object> auth = (Map<String, Object>) result.get("result");
        assertTrue(auth.containsKey("user"));
        assertTrue(auth.containsKey("access_token"));
        assertEquals(user, auth.get("user"));
        assertEquals("jwtToken", auth.get("access_token"));

        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtConfig, times(1)).generateToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void testLogin() {
        // Mock dependencies
        AuthRequest request = new AuthRequest("test@example.com", "password");

        User user = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .password("encodedPassword")
                .role(Roles.MENTEE)
                .build();

        Token token = Token.builder()
                .user(user)
                .token("jwtToken")
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtConfig.generateToken(any(User.class))).thenReturn("jwtToken");
        when(tokenRepository.findAllValidTokensByUser(any(UUID.class))).thenReturn(Collections.singletonList(token));

        // Call the method under test
        Map<String, Object> result = authenticationService.login(request);

        // Verify the result
        assertNotNull(result);
        assertTrue(result.containsKey("result"));
        Map<String, Object> loginResult = (Map<String, Object>) result.get("result");
        assertTrue(loginResult.containsKey("userid"));
        assertTrue(loginResult.containsKey("access_token"));
        assertEquals(user, loginResult.get("userid"));
        assertEquals("jwtToken", loginResult.get("access_token"));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(jwtConfig, times(1)).generateToken(any(User.class));
        verify(tokenRepository, times(1)).findAllValidTokensByUser(any(UUID.class));
        verify(tokenRepository, times(1)).saveAll(anyList());
        // verify(authenticationService, times(1)).saveUserToken(any(User.class), anyString());
        // verify(authenticationService, times(1)).revokeAllUserTokens(any(User.class)); Verify tokens are being saved and old ones being ivalidated
    }

}