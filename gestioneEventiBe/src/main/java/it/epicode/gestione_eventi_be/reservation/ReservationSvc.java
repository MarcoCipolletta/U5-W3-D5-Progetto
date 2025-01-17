package it.epicode.gestione_eventi_be.reservation;

import it.epicode.gestione_eventi_be.event.Event;
import it.epicode.gestione_eventi_be.event.EventSvc;
import it.epicode.gestione_eventi_be.exception.NoSeatsAvailable;
import it.epicode.gestione_eventi_be.exception.NotSameOrganizerEvent;
import it.epicode.gestione_eventi_be.user.normal_user.NormalUser;
import it.epicode.gestione_eventi_be.user.normal_user.NormalUserSvc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;


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
        if(e.getAvailableSeats()>0 && e.getAvailableSeats() >= reservationCreateRequest.getSeatsBooked()){
        e.setAvailableSeats(e.getAvailableSeats() - reservationCreateRequest.getSeatsBooked());
        } else {
            throw new NoSeatsAvailable("Not enough available seats");
        }
        eventSvc.update(e);
        normalUserSvc.update(normalUserser);
        return "Reservation created";
    }

    public List<Reservation> findByUser(User user){
        NormalUser normalUserser = normalUserSvc.findByAppUser(user.getUsername());
        return normalUserser.getReservations();
    }

    @Transactional
    public String delete(Long id, User user){
        NormalUser normalUserser = normalUserSvc.findByAppUser(user.getUsername());
        Reservation reservation = reservationRepo.findById(id).orElse(null);
        if (reservation == null){
            throw new EntityNotFoundException("Reservation not found");
        }
        if(reservation.getUserId().equals(normalUserser.getId())) {
            Event e = eventSvc.findById(reservation.getEventId());
            e.setAvailableSeats(e.getAvailableSeats() + reservation.getSeatsBooked());
            eventSvc.update(e);
            reservationRepo.deleteById(id);
            return "Reservation deleted";
        } else {
            throw new NotSameOrganizerEvent("user not the same");
        }
    }
}
