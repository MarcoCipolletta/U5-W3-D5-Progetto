package it.epicode.gestione_eventi_be.user.organizer;

import it.epicode.gestione_eventi_be.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Organizer findByAppUser(AppUser appUser);

}
