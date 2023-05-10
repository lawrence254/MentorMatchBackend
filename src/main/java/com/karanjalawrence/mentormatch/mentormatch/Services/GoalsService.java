package com.karanjalawrence.mentormatch.mentormatch.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.karanjalawrence.mentormatch.mentormatch.Domain.Goal;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;

public interface GoalsService {
    Goal createGoal(Goal goal);
    List<Goal> getAllGoals();
    Optional<List<Goal>> getGoalsByUserID(UserDetails user);
    Optional<Goal> getGoalById(UUID goal_id);
    Goal updateGoal(Goal goal);
    void deleteGoalById(UUID id);
}
