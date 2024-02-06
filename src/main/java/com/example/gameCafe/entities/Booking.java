package com.example.gameCafe.entities;

import com.example.gameCafe.entities.enums.PaymentMethod;
import com.example.gameCafe.entities.enums.PromoCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime checkin;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime checkout;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "promo_code")
    @Enumerated(EnumType.STRING)
    private PromoCode promoCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Booking(LocalDateTime checkin, LocalDateTime checkout, User user, Room room) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.user = user;
        this.room = room;
    }

    public Booking(LocalDateTime checkin, LocalDateTime checkout, User user, Room room, PaymentMethod paymentMethod) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.user = user;
        this.room = room;
        this.paymentMethod = paymentMethod;
    }
}
