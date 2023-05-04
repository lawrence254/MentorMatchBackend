package com.karanjalawrence.mentormatch.mentormatch.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserDetailsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserDetailsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsImpl implements UserDetailsService{

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails createUserDetails(UserDetails user) {
        return userDetailsRepository.save(user);
    }

    @Override
    public Optional<UserDetails> getUserDetailsById(UUID id) {
        return userDetailsRepository.findById(id);
    }

    @Override
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetails updateUserDetails(UserDetails user) {
        return userDetailsRepository.save(user);
    }

    @Override
    public void deleteUserDetailsByUUID(UUID id) {
        userDetailsRepository.deleteById(id);
    }
    
}
