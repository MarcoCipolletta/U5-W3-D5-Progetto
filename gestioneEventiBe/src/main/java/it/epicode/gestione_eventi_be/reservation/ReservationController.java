package it.epicode.gestione_eventi_be.reservation;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationSvc reservationSvc;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createReservation(@RequestBody ReservationCreateRequest reservationCreateRequest, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(reservationSvc.createReservation(reservationCreateRequest, user), HttpStatus.CREATED);
    }
}
