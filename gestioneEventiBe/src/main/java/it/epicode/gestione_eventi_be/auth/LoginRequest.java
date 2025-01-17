package it.epicode.gestione_eventi_be.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String identifier;
    private String password;
}
