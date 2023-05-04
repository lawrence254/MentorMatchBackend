package com.karanjalawrence.mentormatch.mentormatch.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, UUID>{
    
}
