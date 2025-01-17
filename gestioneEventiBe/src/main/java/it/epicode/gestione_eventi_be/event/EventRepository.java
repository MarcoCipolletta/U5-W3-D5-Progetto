package it.epicode.gestione_eventi_be.event;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {
    Boolean existsByTitle(String title);
}
