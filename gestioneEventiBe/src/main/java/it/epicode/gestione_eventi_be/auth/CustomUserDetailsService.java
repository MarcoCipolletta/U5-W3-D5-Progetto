package it.epicode.gestione_eventi_be.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String identifier)  {
        AppUser user = appUserRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        System.out.println(user.getPassword());
        String[] roles = user.getRoles()
                .stream()
                .map(Enum::name) // Converte l'enum in una stringa (es: "ROLE_ADMIN")
                .toArray(String[]::new);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
