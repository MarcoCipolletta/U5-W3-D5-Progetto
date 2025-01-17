package it.epicode.gestione_eventi_be.reservation;

import it.epicode.gestione_eventi_be.event.Event;
import it.epicode.gestione_eventi_be.user.BasicUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private BasicUser user;

    @Column(nullable = false)
    private int seatsBooked;


}
