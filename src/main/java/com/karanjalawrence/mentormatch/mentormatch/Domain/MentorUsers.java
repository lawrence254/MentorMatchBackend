package com.karanjalawrence.mentormatch.mentormatch.Domain;


import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentor_users")
public class MentorUsers {
    @Id
    @GeneratedValue
    private UUID user_uuid;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_user_id")
    private User user;
    private String first_name;
    private String last_name;
    private String location;
    @Column(name = "experience_yrs")
    private Integer experience;
    @Column(name = "experience_narrative")
    private String description;
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;
}
