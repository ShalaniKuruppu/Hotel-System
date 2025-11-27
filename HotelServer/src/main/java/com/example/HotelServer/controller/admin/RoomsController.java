package com.example.HotelServer.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HotelServer.dto.RoomDto;
import com.example.HotelServer.services.admin.rooms.RoomsService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RoomsController {
    private final RoomsService roomsService;

    @PostMapping("/room")
    public ResponseEntity<?> postRoom(@RequestBody RoomDto roomDto) {
        boolean success = roomsService.postRoom(roomDto);
        System.out.println(success);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    } 
    
    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAllRooms(@PathVariable int pageNumber) {
        System.out.println("=== INSIDE getAllRooms Controller ===");
        System.out.println("Page Number: " + pageNumber);
        return ResponseEntity.ok(roomsService.getAllRooms(pageNumber));
    }

    
    
    
}
