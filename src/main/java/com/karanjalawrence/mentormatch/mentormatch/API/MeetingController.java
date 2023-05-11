package com.karanjalawrence.mentormatch.mentormatch.API;

import java.text.ParseException;
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
import com.karanjalawrence.mentormatch.mentormatch.Domain.Meeting;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserDetailsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.MeetingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;
    private final ObjectMapper objectMapper;
    private final UserDetailsRepository userDetailsRepository;

    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestParam String with, @RequestParam String user, @RequestBody Meeting meeting) throws ParseException{

        UserDetails userD = userDetailsRepository.findById(UUID.fromString(user)).get();
        UserDetails withD = userDetailsRepository.findById(UUID.fromString(with)).get();

        meeting.setUser(userD);
        meeting.setWith(withD);

        return ResponseEntity.ok().body(meetingService.createMeeting(meeting));
    }

    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings(){
        return ResponseEntity.ok().body(meetingService.getAllMeetings());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Meeting>> getMeetingById(@PathVariable String id){
        return ResponseEntity.ok().body(meetingService.getMeetingById(UUID.fromString(id)));
    }

    @GetMapping("{userId}/")
    public ResponseEntity<Optional<List<Meeting>>> getAllMeetingsByUser(@PathVariable String userId){
        UserDetails user = userDetailsRepository.findById(UUID.fromString(userId)).get();
        return ResponseEntity.ok().body(meetingService.getAllUserMeetings(user));
    }

    @GetMapping("mentor/{userId}")
    public ResponseEntity<Optional<List<Meeting>>> getAllMentorMeetings(@PathVariable String userId) {
        UserDetails user = userDetailsRepository.findById(UUID.fromString(userId)).get();
        return ResponseEntity.ok().body(meetingService.getAllMentorMeetings(user));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable String id, @RequestBody Map<String, Object> details) throws JsonMappingException{
        Meeting meet = meetingService.getMeetingById(UUID.fromString(id)).get();
        
        var updatedMeeting = objectMapper.updateValue(meet, details);

        return ResponseEntity.ok().body(meetingService.updateMeeting(updatedMeeting));
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteMeeting(@RequestParam String id){
        try {
            meetingService.deleteMeetingById(UUID.fromString(id));
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete meeting due to: "+e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Meeting has been deleted successfully");
    }
}
