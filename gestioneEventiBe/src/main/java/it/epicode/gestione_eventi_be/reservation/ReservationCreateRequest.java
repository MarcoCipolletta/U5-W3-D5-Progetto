package it.epicode.gestione_eventi_be.reservation;

import it.epicode.gestione_eventi_be.event.Event;
import lombok.Data;

@Data
public class ReservationCreateRequest {
    private Long eventId;
    private int seatsBooked;

}
