package com.karanjalawrence.mentormatch.mentormatch.API;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Goal;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Implementations.GoalsImplementation;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.GoalsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserDetailsRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goals")
public class GoalsController {
    private final GoalsRepository goalsRepository;
    private final GoalsImplementation goalsImplementation;
    private final ObjectMapper objectMapper;
    private final UserDetailsRepository userDetailsRepository;

    @PostMapping("{id}")
    public ResponseEntity<Goal> createGoal(@PathVariable String id, @RequestBody Goal goal){
        UserDetails user = userDetailsRepository.findById(UUID.fromString(id)).get();
        goal.setUser(user);        
        return ResponseEntity.ok().body(goalsImplementation.createGoal(goal));
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals(){
        return ResponseEntity.ok().body(goalsImplementation.getAllGoals());
    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<Goal>> getGoalById(@PathVariable String id){
        return ResponseEntity.ok().body(goalsImplementation.getGoalById(UUID.fromString(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Optional<List<Goal>>> getGoalsByUser(@RequestParam String user){
        UserDetails userD = userDetailsRepository.findById(UUID.fromString(user)).get();
        return ResponseEntity.ok().body(goalsImplementation.getGoalsByUserID(userD));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Goal> updateGoalbyID(@PathVariable String id, @RequestBody Map<String, Object> goal) throws JsonMappingException{
        Goal existinGoal = goalsRepository.findById(UUID.fromString(id)).get();
        
        var newGoal = objectMapper.updateValue(existinGoal, goal);
        return ResponseEntity.ok().body(goalsImplementation.updateGoal(newGoal));
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGoalById(@PathVariable String id){
        try {
            goalsImplementation.deleteGoalById(UUID.fromString(id));
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete the goal because of: "+e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The goal was deleted successfully!");
    }
}
