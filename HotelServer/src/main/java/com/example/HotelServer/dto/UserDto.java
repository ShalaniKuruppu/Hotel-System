package com.example.HotelServer.dto;

import com.example.HotelServer.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
        private Long id;
        private String name;
        private String email;
        private UserRole userRole;

}

