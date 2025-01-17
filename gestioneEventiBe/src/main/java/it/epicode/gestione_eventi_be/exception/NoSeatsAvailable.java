package it.epicode.gestione_eventi_be.exception;

public class NoSeatsAvailable extends RuntimeException {
    public NoSeatsAvailable(String message) {
        super(message);
    }
}
