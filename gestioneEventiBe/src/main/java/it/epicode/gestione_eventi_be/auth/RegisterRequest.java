package it.epicode.gestione_eventi_be.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;

    private Role role;
}
