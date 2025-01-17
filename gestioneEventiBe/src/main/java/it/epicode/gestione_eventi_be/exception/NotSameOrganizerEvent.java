package it.epicode.gestione_eventi_be.exception;

public class NotSameOrganizerEvent extends RuntimeException {
    public NotSameOrganizerEvent(String message) {
        super(message);
    }
}
