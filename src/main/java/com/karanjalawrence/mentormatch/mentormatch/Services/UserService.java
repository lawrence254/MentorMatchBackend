package com.karanjalawrence.mentormatch.mentormatch.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.karanjalawrence.mentormatch.mentormatch.Domain.User;

public interface UserService {
    User createUserAccount(User user);

    List<User> bulkCreateUsers(List<User> users);

    List<User> getAllUsers();

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUUID(UUID uuid);

    User updateUserDetails(User updatedDetails);

    void deleteUserByUUID(UUID uuid);

}
