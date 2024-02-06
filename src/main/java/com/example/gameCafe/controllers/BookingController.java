package com.example.gameCafe.controllers;

import com.example.gameCafe.entities.Booking;
import com.example.gameCafe.entities.Room;
import com.example.gameCafe.entities.User;
import com.example.gameCafe.entities.enums.PaymentMethod;
import com.example.gameCafe.entities.enums.PromoCode;
import com.example.gameCafe.services.BookingService;
import com.example.gameCafe.services.RoomService;
import com.example.gameCafe.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.findAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Integer id) {
        return bookingService.findBookingById(id);
    }

    @PostMapping
    public Booking saveBooking(@RequestBody @Valid Booking newBooking) {
        return bookingService.saveBooking(newBooking);
    }

    @PutMapping("/{id}")
    public Booking updateBookingById(@PathVariable Integer id, @RequestBody Booking newBooking) throws Exception {
        return bookingService.updateBookingById(id, newBooking);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingById(@PathVariable Integer id) {
        bookingService.deleteBookingById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Booking> updateCheckin(@PathVariable Integer id, @RequestParam LocalDateTime newCheckin) {
        Booking booking = bookingService.findBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        booking.setCheckin(newCheckin);
        Booking updatedBooking = bookingService.saveBooking(booking);

        return ResponseEntity.ok(updatedBooking);
    }

    @PatchMapping("/updatePromoCode/{id}")
    public ResponseEntity<Booking> updatePromoCode(@PathVariable Integer id, @RequestParam PromoCode newPromoCode) {
        Booking booking = bookingService.findBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        booking.setPromoCode(newPromoCode);
        Booking updatedBooking = bookingService.saveBooking(booking);

        return ResponseEntity.ok(updatedBooking);
    }

    @PostMapping("/makeReservation")
    public Booking makeReservation(@RequestParam Integer userId,
                                   @RequestParam Integer roomId,
                                   @RequestParam PaymentMethod paymentMethod,
                                   @RequestParam LocalDateTime checkin,
                                   @RequestParam LocalDateTime checkout){

        User user = userService.findUserById(userId);
        Room room = roomService.findRoomById(roomId);

        return bookingService.makeReservation(user,room,paymentMethod,checkin,checkout);
    }


}
