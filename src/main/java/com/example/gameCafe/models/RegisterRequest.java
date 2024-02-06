package com.example.gameCafe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
