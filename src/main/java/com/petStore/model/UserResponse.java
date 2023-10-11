package com.petStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private Double Budget;
}
