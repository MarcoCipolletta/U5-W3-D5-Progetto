package it.epicode.gestione_eventi_be.auth;

import it.epicode.gestione_eventi_be.exception.AlreadyExistsException;
import it.epicode.gestione_eventi_be.exception.EmailAlreadyUsedException;
import it.epicode.gestione_eventi_be.user.BasicUser;
import it.epicode.gestione_eventi_be.user.normal_user.NormalUser;
import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public String registerUser(@Valid RegisterRequest registerRequest) {
        if (appUserRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyUsedException("Email already used");
        }
        if (appUserRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AlreadyExistsException("Username already used");
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(registerRequest.getEmail());
        appUser.setUsername(registerRequest.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        appUser.setRoles(registerRequest.getRoles());
        if (registerRequest.getRoles().contains(Role.ROLE_USER)) {
        appUser.setProfile(new NormalUser());
        } else if (registerRequest.getRoles().contains(Role.ROLE_ORGANIZER)) {
            appUser.setProfile(new Organizer());
        }

        appUserRepository.save(appUser);
        return "Registration success";
    }

    public AppUser findByUsername(String username) {
        if (!appUserRepository.existsByUsername(username)) {
            throw new EntityNotFoundException("User not found");
        }
        return appUserRepository.findByUsername(username).get();
    }

    public Boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public String authenticateUser(@Valid LoginRequest loginRequest) {
        try {
            System.out.println("NEL TRY");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getIdentifier(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("UserDetails: " + userDetails);
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new SecurityException("Credentials not valid", e);
        }
    }
}
