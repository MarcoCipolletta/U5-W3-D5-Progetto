package it.epicode.gestione_eventi_be.event;

import it.epicode.gestione_eventi_be.exception.AlreadyExistsException;
import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import it.epicode.gestione_eventi_be.user.organizer.OrganizerSvc;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class EventSvc {
    private final EventRepository eventRepo;
    private final OrganizerSvc organizerSvc;

    @Transactional
    public String createEvent(@Valid EventCreateRequest eventCreateRequest, User user) {
        if (eventRepo.existsByTitle(eventCreateRequest.getTitle())) {
            throw new AlreadyExistsException("Event with title " + eventCreateRequest.getTitle() + " already exists");
        }
        Organizer organizer = organizerSvc.findByAppUser(user.getUsername());

        Event event = new Event();
        event.setTitle(eventCreateRequest.getTitle());
        event.setDescription(eventCreateRequest.getDescription());
        event.setLocation(eventCreateRequest.getLocation());
        event.setDate(eventCreateRequest.getDate());
        event.setTotalSeats(eventCreateRequest.getTotalSeats());
        event.setAvailableSeats(eventCreateRequest.getTotalSeats());
        event.setOrganizer(organizer);
        organizer.getOrganizedEvents().add(event);
        eventRepo.save(event);
        organizerSvc.update(organizer);
        return "Event created";
    }

    public Event findById(Long id) {
        if(!eventRepo.existsById(id)) {
            throw new EntityNotFoundException("Event not found");
        }
        return eventRepo.findById(id).get();
    }

    public Event update(Long id, EventCreateRequest request) {
        Event e = findById(id);

        BeanUtils.copyProperties(request, e);
        return eventRepo.save(e);
    }

    public String delete(Long id) {
        Event e = findById(id);
        eventRepo.deleteById(e.getId());
        return "Event deleted";
    }

    public List<Event> findAll(){
        return eventRepo.findAll();
    }

}

