package it.epicode.gestione_eventi_be.auth;

import it.epicode.gestione_eventi_be.user.normal_user.NormalUser;
import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Service
@Validated
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AppUser registerUser(@Valid RegisterRequest registerRequest) {
        if (appUserRepository.existsByUsername(registerRequest.getUsername())) {
            throw new EntityExistsException("Username già in uso");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(registerRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        if (registerRequest.getRole() == Role.ROLE_USER) {
        appUser.setRoles((Set.of(Role.ROLE_USER)));
        appUser.setProfile(new NormalUser());
        } else if (registerRequest.getRole() == Role.ROLE_ADMIN) {
            appUser.setRoles((Set.of(Role.ROLE_ADMIN)));

        } else if (registerRequest.getRole() == Role.ROLE_ORGANIZER) {
            appUser.setRoles((Set.of(Role.ROLE_ORGANIZER)));
            appUser.setProfile(new Organizer());
        }

        return appUserRepository.save(appUser);
    }

    public AppUser findByUsername(String username) {
        if(!appUserRepository.existsByUsername(username)) {
            throw new EntityNotFoundException("Utente non trovato con username: " + username);
        }
        return appUserRepository.findByUsername(username).get();
    }

    public String authenticateUser(String username, String password)  {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new SecurityException("Credenziali non valide", e);
        }
    }


    public AppUser loadUserByUsername(String username)  {
        AppUser appUser = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));


        return appUser;
    }
}
