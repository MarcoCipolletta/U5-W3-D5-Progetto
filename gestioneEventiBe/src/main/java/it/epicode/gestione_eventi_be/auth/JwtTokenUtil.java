package it.epicode.gestione_eventi_be.auth;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Autowired
    private AppUserRepository appUserService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;

    // Estrae il nome utente dal token JWT
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Estrae la data di scadenza dal token JWT
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Estrae un claim specifico dal token JWT
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Estrae tutti i claims dal token JWT
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica se il token JWT è scaduto
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Genera un token JWT per l'utente, includendo i ruoli
    public String generateToken(UserDetails userDetails) {
        AppUser appUser = appUserService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<String> roles = appUser.getRoles()
                .stream()
                .map(Enum::name) // Converte gli enum in stringhe
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles) // Aggiunge i ruoli come claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Estrae i ruoli dal token JWT
    public Set<Role> getRolesFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        List<String> rolesAsString = ((List<?>) claims.get("roles"))
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return rolesAsString.stream()
                .map(Role::valueOf) // Converte le stringhe in enum
                .collect(Collectors.toSet());
    }


    // Valida il token JWT
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final Set<Role> roles = getRolesFromToken(token); // Ottieni i ruoli dal token

        // Puoi fare ulteriori validazioni sui ruoli qui, se necessario
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
