package com.karanjalawrence.mentormatch.mentormatch.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.karanjalawrence.mentormatch.mentormatch.Domain.Meeting;
import com.karanjalawrence.mentormatch.mentormatch.Domain.UserDetails;
import com.karanjalawrence.mentormatch.mentormatch.Repositories.MeetingRepository;
import com.karanjalawrence.mentormatch.mentormatch.Services.MeetingService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class MeetingImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    @Override
    public Meeting createMeeting(Meeting details) {
        return meetingRepository.save(details);
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public Optional<Meeting> getMeetingById(UUID id) {
        return meetingRepository.findById(id);
    }

    @Override
    public Optional<List<Meeting>> getAllUserMeetings(UserDetails user) {
        return meetingRepository.findByUser(user);
    }

    @Override
    public Optional<List<Meeting>> getAllMentorMeetings(UserDetails user) {
        return meetingRepository.findByWith(user);
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public void deleteMeetingById(UUID id) {
        meetingRepository.deleteById(id);
    }
    
}
