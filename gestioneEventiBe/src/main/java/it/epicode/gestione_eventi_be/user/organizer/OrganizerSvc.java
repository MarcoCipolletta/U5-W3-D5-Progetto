package it.epicode.gestione_eventi_be.user.organizer;

import it.epicode.gestione_eventi_be.auth.AppUser;
import it.epicode.gestione_eventi_be.auth.AppUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizerSvc {
    private final OrganizerRepository organizerRepo;
    private final AppUserService appUserService;

    public Organizer findById(Long id) {
        if(!organizerRepo.existsById(id)) {
            throw new EntityNotFoundException("Organizer not found");
        }
        return organizerRepo.findById(id).get();
    }

    public Organizer findByAppUser(String username){

      AppUser appUser = appUserService.findByUsername(username);
      return organizerRepo.findByAppUser(appUser);
    }

    public Organizer update(Organizer organizer) {
        return organizerRepo.save(organizer);
    }
}
