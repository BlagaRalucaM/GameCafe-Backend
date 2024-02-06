package com.example.gameCafe.repositories;

import com.example.gameCafe.entities.Booking;
import com.example.gameCafe.entities.Room;
import com.example.gameCafe.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Booking findBookingById(Integer id);

//    List<Booking> findByUserAndTimeRange(User user, LocalDateTime checkin, LocalDateTime checkout);

    List<Booking> findByRoomAndCheckinLessThanEqualAndCheckoutGreaterThanEqual(
            Room room,
            LocalDateTime checkin,
            LocalDateTime checkout);

    List<Booking> findByUser(User user);
}
