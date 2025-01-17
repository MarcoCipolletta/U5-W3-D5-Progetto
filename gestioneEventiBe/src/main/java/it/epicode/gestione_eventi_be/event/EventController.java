package it.epicode.gestione_eventi_be.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventSvc eventSvc;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return new ResponseEntity<>(eventSvc.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createEvent(@RequestBody EventCreateRequest eventCreateRequest, @AuthenticationPrincipal User user) {

        return new ResponseEntity<>(eventSvc.createEvent(eventCreateRequest, user), HttpStatus.CREATED);
    }
}
