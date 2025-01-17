package it.epicode.gestione_eventi_be.event;


import it.epicode.gestione_eventi_be.user.organizer.Organizer;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aspectj.lang.annotation.After;

import java.time.LocalDateTime;

@Data
public class EventCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;
    @NotEmpty(message = "Description is required")
    private String description;

    @NotBlank(message = "Location is required")
    private String location;

    @Future(message = "Date must be in the future")
    private LocalDateTime date;

    @NotNull(message = "Total seats is required")
    private int totalSeats;

}
