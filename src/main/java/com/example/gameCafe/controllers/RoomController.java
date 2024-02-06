package com.example.gameCafe.controllers;

import com.example.gameCafe.entities.Room;
import com.example.gameCafe.services.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id) {
        return roomService.findRoomById(id);
    }

    @PostMapping
    public Room saveRoom(@RequestBody @Valid Room newRoom) {
        return roomService.saveRoom(newRoom);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomById(@PathVariable Integer id) {
        roomService.deleteRoomById(id);
    }

    @PutMapping("/{id}")
    public Room updateRoomById(@PathVariable Integer id, @Valid @RequestBody Room newRoom) throws Exception {
        return roomService.updateRoomById(id, newRoom);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Room> updateRoomNumber(@PathVariable Integer id, @RequestParam short newRoomNumber) {
        Room room = roomService.findRoomById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        room.setRoomNumber(newRoomNumber);
        Room updatedRoom = roomService.saveRoom(room);

        return ResponseEntity.ok(updatedRoom);
    }

}
