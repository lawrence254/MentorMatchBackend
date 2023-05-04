package com.karanjalawrence.mentormatch.mentormatch.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserSkill;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long>{
    Optional<List<UserSkill>> findUserSkillByUser(UserDetails user);
}
