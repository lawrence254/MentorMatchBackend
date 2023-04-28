package com.karanjalawrence.mentormatch.mentormatch.Repositories;

import java.util.Optional;
import java.util.UUID;



import com.karanjalawrence.mentormatch.mentormatch.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email_address);
}
