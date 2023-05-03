package com.karanjalawrence.mentormatch.mentormatch.Services;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;

public interface UserDetailsService {
    UserDetails createUserDetails(UserDetails user);
    Optional<UserDetails> getUserDetailsById(UUID id);
    List<UserDetails> getAllUsers();
    UserDetails updateUserDetails(UserDetails user);
    void deleteUserDetailsByUUID(UUID id);
}
