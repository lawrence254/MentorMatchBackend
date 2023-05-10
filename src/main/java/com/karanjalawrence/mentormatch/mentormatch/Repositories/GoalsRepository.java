package com.karanjalawrence.mentormatch.mentormatch.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karanjalawrence.mentormatch.mentormatch.Domain.Goal;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;

public interface GoalsRepository extends JpaRepository< Goal, UUID>{
    Optional<List<Goal>> getByUser(UserDetails user);
}
