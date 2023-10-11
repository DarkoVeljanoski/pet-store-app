package com.petStore.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @ManyToOne
    private User Owner;
    private String Name;
    private Type PetType;
    private String Description;
    private LocalDate DateOfBirth;
    private Double Price;
    private Integer Rating;
}
