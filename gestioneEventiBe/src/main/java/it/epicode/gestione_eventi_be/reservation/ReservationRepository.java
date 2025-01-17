package it.epicode.gestione_eventi_be.reservation;

import it.epicode.gestione_eventi_be.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
