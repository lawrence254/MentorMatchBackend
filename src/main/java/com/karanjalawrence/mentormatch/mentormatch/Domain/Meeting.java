package com.karanjalawrence.mentormatch.mentormatch.Domain;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue
    private UUID meetId;
    private MeetMethod method;
    @Column(name = "meeting_link")
    private String link;
    @Column(name = "organizer")
    private UserDetails user;
    @Column(name = "meet_with")
    private UserDetails with;
    @Column(name = "meeting_date")
    private Date mDate;
    private MStatus status;
    @Column(name ="reschedule_date", nullable = true)
    private Date rescheduleTo;
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;
}
