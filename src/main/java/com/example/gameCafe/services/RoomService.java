package com.example.gameCafe.services;

import com.example.gameCafe.entities.Room;
import com.example.gameCafe.repositories.RoomRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;


    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Room findRoomById(Integer id) {
        return roomRepository.findRoomById(id);
    }

    public Room saveRoom(@Valid Room newRoom) {
        return roomRepository.save(newRoom);
    }

    public void deleteRoomById(Integer id) {
        roomRepository.deleteById(id);
    }

    public Room updateRoomById(Integer id, Room newRoom) throws Exception {
        if (newRoom != null) {
            Room replacedRoom = roomRepository.findRoomById(id);
            if (replacedRoom != null) {
                replacedRoom.setRoomNumber(newRoom.getRoomNumber());
                replacedRoom.setGameType(newRoom.getGameType());
            } else throw new Exception("Replaced Room not found");
        }
        throw new Exception("New Room not found");
    }

    public Room updateRoomNumber(Integer id, short newRoomNumber) throws Exception {
        Room room = roomRepository.findById(id).orElse(null);

        if (room != null) {
            room.setRoomNumber(newRoomNumber);
            return roomRepository.save(room);
        }
        throw new Exception("Room not found");
    }

}
