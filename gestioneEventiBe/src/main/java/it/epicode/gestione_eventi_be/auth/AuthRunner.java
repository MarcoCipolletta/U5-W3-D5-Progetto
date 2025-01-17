package it.epicode.gestione_eventi_be.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione dell'utente admin se non esiste
        if (!appUserRepository.existsByUsername("admin")) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername("admin");
            registerRequest.setPassword("adminpwd");
            registerRequest.setRole(Role.ROLE_ADMIN);
            appUserService.registerUser(registerRequest);
        }

        // Creazione dell'utente user se non esiste
        if (!appUserRepository.existsByUsername("user")) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername("user");
            registerRequest.setPassword("userpwd");
            registerRequest.setRole(Role.ROLE_USER);
            appUserService.registerUser(registerRequest);
        }

        // Creazione dell'utente organizer se non esiste
        if (!appUserRepository.existsByUsername("organizer")) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setUsername("organizer");
            registerRequest.setPassword("organizerpwd");
            registerRequest.setRole(Role.ROLE_ORGANIZER);
            appUserService.registerUser(registerRequest);
        }

        System.out.println(appUserRepository.findAll());

    }
}
