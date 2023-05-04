package com.karanjalawrence.mentormatch.mentormatch.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUserAccount(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUUID(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public User updateUserDetails(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserByUUID(UUID uuid) {
        userRepository.deleteById(uuid);
    }

}
