package com.karanjalawrence.mentormatch.mentormatch.Services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karanjalawrence.mentormatch.mentormatch.Configs.JWTConfig;
import com.karanjalawrence.mentormatch.mentormatch.Domain.AuthRequest;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Token;
import com.karanjalawrence.mentormatch.mentormatch.Domain.TokenType;
import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.TokenRepository;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTConfig jwtConfig;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public Map<String, Object> registerUser(User user) {
        var record = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole()).build();

        userRepository.save(record);
        var jwtToken = jwtConfig.generateToken(user);
        saveUserToken(record, jwtToken);

        HashMap<String, Object> auth = new HashMap<String, Object>();
        auth.put("user", user);
        auth.put("access_token", jwtToken);

        HashMap<String, Object> result = new HashMap<>();
        result.put("result", auth);
        return result;
    }

    public Map<String, Object> login(AuthRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found for email: " + request.getEmail()));

        var token = jwtConfig.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, token);

        HashMap<String, Object> loginResult = new HashMap<>();
        loginResult.put("userid", user);
        loginResult.put("access_token", token);

        result.put("result", loginResult);

        return result;
    }

    private void saveUserToken(User user, String token) {
        var jwtToken = Token.builder().user(user).token(token).tokenType(TokenType.BEARER).expired(false).revoked(false)
                .build();
        tokenRepository.save(jwtToken);
    }

    private void revokeAllUserTokens(User user) {
        var validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validTokens.isEmpty())
            return;

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
    }
}
