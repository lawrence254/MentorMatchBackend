package com.karanjalawrence.mentormatch.mentormatch.Services;

import java.util.List;
import java.util.Optional;

import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserSkill;

public interface UserSkillService {
    UserSkill createSkill(UserSkill skill);
    Optional<UserSkill> getSkillById(Long id);
    List<UserSkill> getAllUserSkills();
    Optional<List<UserSkill>> getUserSkillsByUser(UserDetails user);
    UserSkill updateSkill(UserSkill skill);
    void deleteUserSkillById(Long id);

}
