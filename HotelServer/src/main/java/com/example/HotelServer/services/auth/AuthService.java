package com.example.HotelServer.services.auth;
import com.example.HotelServer.dto.SignUpRequest;
import com.example.HotelServer.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignUpRequest signupRequest);
}
