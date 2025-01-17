package it.epicode.gestione_eventi_be.reservation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.epicode.gestione_eventi_be.event.Event;
import it.epicode.gestione_eventi_be.user.BasicUser;
import it.epicode.gestione_eventi_be.user.normal_user.NormalUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Event event;
    public Long getEventId() {
        return event != null ? event.getId() : null;
    }


    @ManyToOne
    @JsonBackReference
    private NormalUser user;
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }


    @Column(nullable = false)
    private int seatsBooked;


}
