package com.karanjalawrence.mentormatch.mentormatch.Domain;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@AllArgsConstructor
@NoArgsConstructor

public class Goal {
    @Id
    @GeneratedValue
    private UUID goalId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_uuid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Hide implementation details for the UserDetails From being returned as part of the JSON response
    private UserDetails user;
    private String[] goal;
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date update_at;
}
