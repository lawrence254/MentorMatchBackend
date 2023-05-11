package com.karanjalawrence.mentormatch.mentormatch.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.karanjalawrence.mentormatch.mentormatch.Domain.Goal;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.GoalsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.GoalsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalsImpl implements GoalsService{

    private final GoalsRepository goalsRepository;
    
    @Override
    public Goal createGoal(Goal goal) {
        return goalsRepository.save(goal);
    }

    @Override
    public List<Goal> getAllGoals() {
        return goalsRepository.findAll();
    }

    @Override
    public Optional<List<Goal>> getGoalsByUserID(UserDetails user) {
        return goalsRepository.getByUser(user);
    }

    @Override
    public Optional<Goal> getGoalById(UUID goal_id) {
        return goalsRepository.findById(goal_id);
    }

    @Override
    public void deleteGoalById(UUID id) {
        goalsRepository.deleteById(id);
    }

    @Override
    public Goal updateGoal(Goal goal) {
        return goalsRepository.save(goal);
    }
    
}
