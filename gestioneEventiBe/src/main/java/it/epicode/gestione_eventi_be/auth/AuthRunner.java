package it.epicode.gestione_eventi_be.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(1)
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione dell'utente admin se non esiste
        Boolean adminUser = appUserService.existsByUsername("admin");
        if (!adminUser) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail("admin@admin.it");
            registerRequest.setUsername("admin");
            registerRequest.setPassword("adminpwd");
            registerRequest.setRoles(Set.of(Role.ROLE_ADMIN));
            appUserService.registerUser(registerRequest);
        }


        // Creazione dell'utente user se non esiste
        Boolean normalUser = appUserService.existsByUsername("user");
        if (!normalUser) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail("user@user.it");
            registerRequest.setUsername("user");
            registerRequest.setPassword("userpwd");
            registerRequest.setRoles(Set.of(Role.ROLE_USER));

            appUserService.registerUser(registerRequest);
        }

        Boolean organizer = appUserService.existsByUsername("organizer");
        if (!organizer) {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail("organizer@organizer.it");
            registerRequest.setUsername("organizer");
            registerRequest.setPassword("organizerpwd");
            registerRequest.setRoles(Set.of(Role.ROLE_ORGANIZER));
            appUserService.registerUser(registerRequest);
        }
    }
}
