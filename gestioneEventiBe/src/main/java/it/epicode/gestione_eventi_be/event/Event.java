package it.epicode.gestione_eventi_be.event;

import it.epicode.gestione_eventi_be.reservation.Reservation;
import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name= "available_seats")
    private int availableSeats;

    @OneToMany(mappedBy = "event")
    private List<Reservation> reservations;

    @ManyToOne
    private Organizer organizer;


}
