package com.karanjalawrence.mentormatch.mentormatch.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karanjalawrence.mentormatch.mentormatch.Domain.Meeting;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.MeetingRepository;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.UserDetailsRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.MeetingService;

public class MeetingControllerTest {

    @Mock
    private UserDetailsController userDetailsController;

    @Mock
    private MeetingService meetingService;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private MeetingController meetingController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    private List<Meeting> setupMeetingList(){
        Meeting meet1 = new Meeting();
        Meeting meet2 = new Meeting();

        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meet1);
        meetings.add(meet2);

        return meetings;
    }

    @Test
    void testCreateMeeting() {
        UUID withId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Meeting meet = new Meeting();
        UserDetails with = new UserDetails();
        UserDetails user = new UserDetails();

        when(userDetailsRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userDetailsRepository.findById(withId)).thenReturn(Optional.of(with));
        when(meetingService.createMeeting(any(Meeting.class))).thenReturn(meet);

        ResponseEntity<Meeting> response = meetingController.createMeeting(withId.toString(), userId.toString(), meet);

        verify(userDetailsRepository,times(1)).findById(userId);
        verify(userDetailsRepository,times(1)).findById(withId);
        verify(meetingService, times(1)).createMeeting(any(Meeting.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meet, response.getBody());


    }

    @Test
    void testDeleteMeeting() {
        UUID meetingId = UUID.randomUUID();

        doNothing().when(meetingService).deleteMeetingById(meetingId);
        ResponseEntity<?> response = meetingController.deleteMeeting(meetingId.toString());

        verify(meetingService, times(1)).deleteMeetingById(meetingId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Meeting has been deleted successfully",response.getBody());
    }

    @Test
    void testDeleteMeetingWithException(){
        UUID meetingId = UUID.randomUUID();

        String errorMessage = "Failed to delete meeting";

        doThrow(new RuntimeException(errorMessage)).when(meetingService).deleteMeetingById(meetingId);

        ResponseEntity<?> response = meetingController.deleteMeeting(meetingId.toString());

        verify(meetingService, times(1)).deleteMeetingById(meetingId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to delete meeting due to: "+errorMessage, response.getBody());
    }

    @Test
    void testGetAllMeetings() {

        when(meetingService.getAllMeetings()).thenReturn(setupMeetingList());

        ResponseEntity<List<Meeting>> response = meetingController.getAllMeetings();

        verify(meetingService, times(1)).getAllMeetings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(setupMeetingList(), response.getBody());


    }

    @Test
    void testGetAllMeetingsByUser() {
        UserDetails user = new UserDetails();
        UUID id = UUID.randomUUID();

        Optional<List<Meeting>> meetings = Optional.of(setupMeetingList());

        when(userDetailsRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(meetingService.getAllUserMeetings(any(UserDetails.class))).thenReturn(meetings);

        ResponseEntity<Optional<List<Meeting>>> response = meetingController.getAllMeetingsByUser(id.toString());

        verify(userDetailsRepository,times(1)).findById(any(UUID.class));
        verify(meetingService, times(1)).getAllUserMeetings(any(UserDetails.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(meetings, response.getBody());

    }

    @Test
    void testGetAllMentorMeetings() {
        UUID id = UUID.randomUUID();
        UserDetails mentor = new UserDetails();

        Optional<List<Meeting>> mentorMeets = Optional.of(setupMeetingList());

        when(userDetailsRepository.findById(any(UUID.class))).thenReturn(Optional.of(mentor));
        when(meetingService.getAllMentorMeetings(any(UserDetails.class))).thenReturn(mentorMeets);

        ResponseEntity<Optional<List<Meeting>>> response = meetingController.getAllMentorMeetings(id.toString());

        verify(userDetailsRepository, times(1)).findById(any(UUID.class));
        verify(meetingService, times(1)).getAllMentorMeetings(any(UserDetails.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mentorMeets, response.getBody());
    }

    @Test
    void testGetMeetingById() {
        UUID id = UUID.randomUUID();

        Meeting meeting = new Meeting();

        when(meetingService.getMeetingById(any(UUID.class))).thenReturn(Optional.of(meeting));

        ResponseEntity<Optional<Meeting>> response = meetingController.getMeetingById(id.toString());

        verify(meetingService, times(1)).getMeetingById(any(UUID.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(meeting), response.getBody());
    }

    @Test
    void testUpdateMeeting() throws JsonMappingException {
        Meeting existing = new Meeting();
        Meeting newMeet = new Meeting();

        UUID id = UUID.randomUUID();

        Map<String, Object> update = new HashMap<>();
        update.put("with", "update");

        when(meetingService.getMeetingById(any(UUID.class))).thenReturn(Optional.of(existing));
        when(objectMapper.updateValue(any(Meeting.class), any(Map.class))).thenReturn(newMeet);
        when(meetingService.updateMeeting(any(Meeting.class))).thenReturn(newMeet);

        ResponseEntity<Meeting> response = meetingController.updateMeeting(id.toString(), update);

        verify(meetingService, times(1)).getMeetingById(any(UUID.class));
        verify(objectMapper, times(1)).updateValue(any(Meeting.class), any(Map.class));
        verify(meetingService, times(1)).updateMeeting(any(Meeting.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newMeet, response.getBody());

    }
}
