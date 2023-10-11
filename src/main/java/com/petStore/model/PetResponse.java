package com.petStore.model;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {

    private Long Id;
    private UserResponse Owner;
    private String Name;
    private Type PetType;
    private String Description;
    private LocalDate DateOfBirth;
    private Double Price;
    private Integer Rating;
}
