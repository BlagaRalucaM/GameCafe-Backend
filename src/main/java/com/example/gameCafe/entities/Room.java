package com.example.gameCafe.entities;

import com.example.gameCafe.entities.enums.GameType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "room_number")
    @Positive(message = "Room number must be positive")
    private short roomNumber;

    @Column(name = "game_type")
    @NotNull(message = "Game type cannot be null")
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @ManyToMany(mappedBy = "rooms")
    private List<User> users;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
