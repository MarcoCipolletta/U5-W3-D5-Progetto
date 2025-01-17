package it.epicode.gestione_eventi_be.reservation;

import it.epicode.gestione_eventi_be.event.Event;
import it.epicode.gestione_eventi_be.event.EventSvc;
import it.epicode.gestione_eventi_be.user.normal_user.NormalUser;
import it.epicode.gestione_eventi_be.user.normal_user.NormalUserSvc;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationSvc {
    private final ReservationRepository reservationRepo;
    private final EventSvc eventSvc;
    private final NormalUserSvc normalUserSvc;


    @Transactional
    public String createReservation(ReservationCreateRequest reservationCreateRequest, User user) {
        Event e = eventSvc.findById(reservationCreateRequest.getEventId());

        NormalUser normalUserser = normalUserSvc.findByAppUser(user.getUsername());
        Reservation reservation = new Reservation();
        reservation.setEvent(e);
        reservation.setUser(normalUserser);
        normalUserser.getReservations().add(reservation);
        reservation.setSeatsBooked(reservationCreateRequest.getSeatsBooked());
        reservationRepo.save(reservation);
        e.setAvailableSeats(e.getAvailableSeats() - reservationCreateRequest.getSeatsBooked());
        eventSvc.update(e);
        normalUserSvc.update(normalUserser);
        return "Reservation created";
    }
}
