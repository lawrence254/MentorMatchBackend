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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserSkill;
import com.karanjalawrence.mentormatch.mentormatch.Implementations.UserSkillServiceImpl;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserDetailsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.UserSkillService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class UserSkillController {
    private final UserSkillService userSkillService;
    private final UserDetailsRepository userDetailsRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("{uuid}")
    public ResponseEntity<UserSkill> createSkill(@PathVariable String uuid, @RequestBody UserSkill skill) {
        UserDetails user = userDetailsRepository.getReferenceById(UUID.fromString(uuid));
        skill.setUser(user);
        return ResponseEntity.ok().body(userSkillService.createSkill(skill));
    }

    @GetMapping
    public ResponseEntity<List<UserSkill>> getAllUserSkills() {
        return ResponseEntity.ok().body(userSkillService.getAllUserSkills());
    }

    @GetMapping("{uuid}")
    public ResponseEntity<Optional<List<UserSkill>>> getAllSkillsForUser(@PathVariable String uuid) {
        UserDetails user = userDetailsRepository.findById(UUID.fromString(uuid)).get();

        return ResponseEntity.ok().body(userSkillService.getUserSkillsByUser(user));
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserSkill> updateSkillDetails(@PathVariable String id, @RequestBody Map<String, Object> skill)
            throws JsonMappingException {
        UserSkill existing = userSkillService.getSkillById(Long.parseLong(id)).get();

        var newDetails = objectMapper.updateValue(existing, skill);
        try {
            userSkillService.updateSkill(newDetails);

        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to Update the selected skill because of: " + e.getMessage());
        }
        return ResponseEntity.ok().body(userSkillService.getSkillById(Long.parseLong(id)).get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserSkill(@PathVariable String id) {
        try {
            userSkillService.deleteUserSkillById(Long.valueOf(id));
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to delete the selected skill because of: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Skill Deleted");
    }

}
