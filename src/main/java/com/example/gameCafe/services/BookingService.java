package com.example.gameCafe.services;

import com.example.gameCafe.entities.Booking;
import com.example.gameCafe.entities.Room;
import com.example.gameCafe.entities.User;
import com.example.gameCafe.entities.enums.PaymentMethod;
import com.example.gameCafe.entities.enums.PromoCode;
import com.example.gameCafe.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    @Autowired
    private final BookingRepository bookingRepository;

    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking findBookingById(Integer id) {
        return bookingRepository.findBookingById(id);
    }

    public Booking saveBooking(Booking newBooking) {
        return bookingRepository.save(newBooking);
    }

    public Booking updateBookingById(Integer id, Booking newBooking) throws Exception {
        if (newBooking != null) {
            Booking replacedBooking = bookingRepository.findBookingById(id);
            if (replacedBooking != null) {
                replacedBooking.setCheckin(newBooking.getCheckin());
                replacedBooking.setCheckout(newBooking.getCheckout());
                replacedBooking.setRoom(newBooking.getRoom());
                replacedBooking.setPromoCode(newBooking.getPromoCode());
                replacedBooking.setUser(newBooking.getUser());
                replacedBooking.setPaymentMethod(newBooking.getPaymentMethod());
                return bookingRepository.save(replacedBooking);
            } else throw new Exception("Replaced Booking not found");
        }
        throw new Exception("New Booking not found");
    }

    public void deleteBookingById(Integer id) {
        bookingRepository.deleteById(id);
    }

    public Booking updateCheckin(Integer id, LocalDateTime newCheckin) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking != null) {
            booking.setCheckin(newCheckin);
            return bookingRepository.save(booking);
        }
        throw new Exception("Booking not found");
    }

    public Booking updatePromoCode(Integer id, PromoCode newPromoCode) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);

        if (booking != null) {
            booking.setPromoCode(newPromoCode);
            return bookingRepository.save(booking);
        }
        throw new Exception("Booking not found");
    }


    public Booking makeReservation(User user, Room room, PaymentMethod paymentMethod, LocalDateTime checkin, LocalDateTime checkout) {


        if (userHasReservationAtTime(user, checkin, checkout)) {
            throw new RuntimeException("User already has a reservation at the specified time");
        }

        if (user == null || room == null || checkin == null || checkout == null) {
            throw new IllegalArgumentException("Booking parameters cannot be null");
        }

        // Date and Time Validations
        if (checkin.isAfter(checkout)) {
            throw new RuntimeException("Invalid reservation time: Start time must be before end time");
        }
        if (checkin.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid reservation time: Start time must be in the future");
        }

        // Availability Check
        if (!isRoomAvailable(room, checkin, checkout)) {
            throw new RuntimeException("The selected room is not available during the specified time");
        }

        // Maximum Reservation Duration
        Duration maxDuration = Duration.ofHours(4);
        if (Duration.between(checkin, checkout).compareTo(maxDuration) > 0) {
            throw new RuntimeException("Maximum reservation duration exceeded");
        }

        Booking newBooking = new Booking(checkin,checkout,user,room,paymentMethod);
        return bookingRepository.save(newBooking);
    }

    private boolean userHasReservationAtTime(User user, LocalDateTime checkin, LocalDateTime checkout) {

        List<Booking> userReservations = bookingRepository.findByUser(user);

        for (Booking reservation : userReservations) {
            LocalDateTime existingCheckin = reservation.getCheckin();
            LocalDateTime existingCheckout = reservation.getCheckout();

            if (checkin.isBefore(existingCheckout) && checkout.isAfter(existingCheckin)) {
                // There is an overlap, indicating that the user has a reservation at the specified time
                return true;
            }
        }
        // No overlap found, the user does not have a reservation at the specified time
        return false;
    }

    public boolean isRoomAvailable(Room room, LocalDateTime checkin, LocalDateTime checkout) {
        List<Booking> conflictingBookings = bookingRepository.findByRoomAndCheckinLessThanEqualAndCheckoutGreaterThanEqual(
                room,
                checkin,
                checkout
        );
        return conflictingBookings.isEmpty();
    }


}
