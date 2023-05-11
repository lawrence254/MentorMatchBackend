package com.karanjalawrence.mentormatch.mentormatch.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.karanjalawrence.mentormatch.mentormatch.Domain.Meeting;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;

public interface MeetingService {
    Meeting createMeeting(Meeting details);
    List<Meeting> getAllMeetings();
    Optional<Meeting> getMeetingById(UUID id);
    Optional<List<Meeting>> getAllUserMeetings(UserDetails user);
    Optional<List<Meeting>> getAllMentorMeetings(UserDetails user);
    Meeting updateMeeting(Meeting meeting);
    void deleteMeetingById(UUID id);
}
