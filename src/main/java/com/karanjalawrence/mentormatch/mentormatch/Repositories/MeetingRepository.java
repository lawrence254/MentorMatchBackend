package com.karanjalawrence.mentormatch.mentormatch.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karanjalawrence.mentormatch.mentormatch.Domain.Meeting;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;

public interface MeetingRepository extends JpaRepository<Meeting, UUID>{
    Optional<List<Meeting>> findByUser(UserDetails user);
    Optional<List<Meeting>> findByWith(UserDetails user);
}
