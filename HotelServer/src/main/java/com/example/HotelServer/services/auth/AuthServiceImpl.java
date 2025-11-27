package com.example.HotelServer.services.auth;
import java.util.Optional;

import com.example.HotelServer.dto.SignUpRequest;
import com.example.HotelServer.entity.User;
import com.example.HotelServer.enums.UserRole;

import org.springframework.stereotype.Service;
import com.example.HotelServer.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.HotelServer.dto.UserDto;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @PostConstruct
    private void createAnAdminAccount(){
            Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
            if(adminAccount.isEmpty()){
                User adminUser = new User();
                adminUser.setName("Admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setUserRole(UserRole.ADMIN);
                adminUser.setPassword(new BCryptPasswordEncoder().encode("admin"));
                userRepository.save(adminUser);
                System.out.println("Admin account created successfully");
            }else{
                System.out.println("Admin account already exists");
            }
    }

    public UserDto createUser(SignUpRequest signupRequest){
        if(userRepository.findFirstByEmail(signupRequest.getEmail()).isPresent()){
            throw new EntityExistsException("User already present with email: " + signupRequest.getEmail());
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser.getUserDto();
    }
}
