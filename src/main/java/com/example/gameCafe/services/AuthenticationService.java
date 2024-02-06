package com.example.gameCafe.services;

import com.example.gameCafe.models.AuthenticationResponse;
import com.example.gameCafe.models.LoginRequest;
import com.example.gameCafe.models.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse login(LoginRequest loginRequest);

    AuthenticationResponse register(RegisterRequest registerRequest);

}
