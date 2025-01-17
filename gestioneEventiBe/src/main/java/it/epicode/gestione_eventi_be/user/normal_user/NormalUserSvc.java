package it.epicode.gestione_eventi_be.user.normal_user;

import it.epicode.gestione_eventi_be.auth.AppUser;
import it.epicode.gestione_eventi_be.auth.AppUserService;
import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NormalUserSvc {
    private final AppUserService appUserService;
    private final NormalUserRepository normalUserRepo;


    public NormalUser findByAppUser(String username){

        AppUser appUser = appUserService.findByUsername(username);
        return normalUserRepo.findByAppUser(appUser);
    }

    public NormalUser update(NormalUser normalUser) {
        return normalUserRepo.save(normalUser);
    }

}
