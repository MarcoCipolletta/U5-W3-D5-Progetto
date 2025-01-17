package it.epicode.gestione_eventi_be.user.normal_user;

import it.epicode.gestione_eventi_be.auth.AppUser;
import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NormalUserRepository extends JpaRepository<NormalUser, Long> {
    NormalUser findByAppUser(AppUser appUser);


}
