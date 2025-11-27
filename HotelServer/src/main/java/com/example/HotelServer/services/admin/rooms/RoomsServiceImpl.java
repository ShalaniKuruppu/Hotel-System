package com.example.HotelServer.services.admin.rooms;

import org.springframework.stereotype.Service;

import com.example.HotelServer.dto.RoomDto;
import com.example.HotelServer.entity.Room;
import com.example.HotelServer.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private final RoomRepository roomRepository;

    public boolean postRoom(RoomDto roomDto){
        try{
            Room room = new Room();

            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);
            System.out.println(room);
            roomRepository.save(room);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
