package com.karanjalawrence.mentormatch.mentormatch.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Goal;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserDetailsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.AuthenticationService;
import com.karanjalawrence.mentormatch.mentormatch.Services.GoalsService;

public class GoalsControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserDetailsRepository userDetailsRepository;
    @Mock
    private UserDetails userDetails;
    @Mock
    private GoalsService goalsService;

    @InjectMocks
    private GoalsController goalsController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateGoal() {
        UUID userId = UUID.randomUUID();
        Goal goal = new Goal();
        
        when(userDetailsRepository.findById(any(UUID.class))).thenReturn(Optional.of(userDetails));

        ResponseEntity<Goal> response = goalsController.createGoal(userId.toString(), goal);
        verify(goalsService, times(1)).createGoal(goal);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteGoalById() {
        UUID id = UUID.randomUUID();
        doNothing().when(goalsService).deleteGoalById(id);
        ResponseEntity<?> response = goalsController.deleteGoalById(id.toString());
        verify(goalsService,times(1)).deleteGoalById(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("The goal was deleted successfully!",response.getBody());
    }

    @Test
    void testGetAllGoals() {
        
        Goal test = new Goal();
        Goal test2 = new Goal();
        List<Goal> goals = new ArrayList<>();
        goals.add(test);
        goals.add(test2);

        when(goalsService.getAllGoals()).thenReturn(goals);


        ResponseEntity<List<Goal>> response = goalsController.getAllGoals();
        verify(goalsService, times(1)).getAllGoals();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(goals, response.getBody());

    }

    @Test
    void testGetGoalById() {
        UUID id = UUID.randomUUID();
        Goal goal = new Goal();
        Optional<Goal> optionalGoal = Optional.of(goal);

        when(goalsService.getGoalById(any(UUID.class))).thenReturn(optionalGoal);

        ResponseEntity<Optional<Goal>> response = goalsController.getGoalById(id.toString());
        verify(goalsService, times(1)).getGoalById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetGoalsByUser() {
        UUID id = UUID.randomUUID();

        UserDetails userDetails = new UserDetails();
        Goal test = new Goal();
        Goal test2 = new Goal();
        List<Goal> goals = new ArrayList<>();
        goals.add(test);
        goals.add(test2);

        Optional<List<Goal>> optionalGoals = Optional.of(goals);

        when(userDetailsRepository.findById(any(UUID.class))).thenReturn(Optional.of(userDetails));
        when(goalsService.getGoalsByUserID(any(UserDetails.class))).thenReturn(optionalGoals);

        ResponseEntity<Optional<List<Goal>>> response = goalsController.getGoalsByUser(id.toString());

        verify(userDetailsRepository,times(1)).findById(any(UUID.class));
        verify(goalsService, times(1)).getGoalsByUserID(any(UserDetails.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(optionalGoals, response.getBody());

    }

    @Test
    void testUpdateGoalbyID() throws JsonMappingException {
        UUID id = UUID.randomUUID();

        Goal goal = new Goal();
        Goal updatedGoal = new Goal();

        Map<String, Object> update= new HashMap<>();
        update.put("goal", "update");

        when(goalsService.getGoalById(any(UUID.class))).thenReturn(Optional.of(goal));
        when(goalsService.updateGoal(any(Goal.class))).thenReturn(updatedGoal);

        ResponseEntity<Goal> response = goalsController.updateGoalbyID(id.toString(), update);

        verify(goalsService, times(1)).getGoalById(any(UUID.class));
        verify(goalsService, times(1)).updateGoal(any(Goal.class));
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(update, response.getBody());
    }
}
