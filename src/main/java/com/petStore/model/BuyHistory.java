package com.petStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @CreationTimestamp
    private LocalDateTime CreatedOn;
    private int numberOfSuccesses;
    private int numberOfFailures;

}
