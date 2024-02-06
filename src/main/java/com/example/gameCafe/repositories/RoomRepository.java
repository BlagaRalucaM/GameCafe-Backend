package com.example.gameCafe.repositories;

import com.example.gameCafe.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findRoomById(Integer id);
}
