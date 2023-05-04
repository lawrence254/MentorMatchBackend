package com.karanjalawrence.mentormatch.mentormatch.Implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserSkill;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserSkillRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserSkillService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSkillServiceImpl implements UserSkillService{
    private final UserSkillRepository userSkillRepository;
    @Override
    public UserSkill createSkill(UserSkill skill) {
        return userSkillRepository.save(skill);
    }
    
    
    @Override
    public List<UserSkill> getAllUserSkills() {
        return userSkillRepository.findAll();
    }
    
    @Override
    public Optional<UserSkill> getSkillById(Long id) {
        return userSkillRepository.findById(id);
    }

    @Override
    public Optional<List<UserSkill>> getUserSkillsByUser(UserDetails user) {
        return userSkillRepository.findUserSkillByUser(user);
    }
    
    @Override
    public UserSkill updateSkill(UserSkill skill) {
        return userSkillRepository.save(skill);
    }
    
    @Override
    public void deleteUserSkillById(Long id) {
        userSkillRepository.deleteById(id);
        
    }


}
