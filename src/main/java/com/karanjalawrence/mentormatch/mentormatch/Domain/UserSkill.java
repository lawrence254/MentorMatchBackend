package com.karanjalawrence.mentormatch.mentormatch.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill {
    @Id
    @GeneratedValue
    private Long skillId;
    @Column(name = "experience_name")
    private String skill;
    @Column(name = "experience_length")
    private int experienceLength;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uuid")
    private UserDetails user;
}
