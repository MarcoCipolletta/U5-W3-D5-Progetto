package it.epicode.gestione_eventi_be.user.organizer;


import it.epicode.gestione_eventi_be.auth.AppUser;
import it.epicode.gestione_eventi_be.event.Event;
import it.epicode.gestione_eventi_be.user.BasicUser;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Organizer extends BasicUser {

    @OneToMany(mappedBy = "organizer")
    private List<Event> organizedEvents = new ArrayList<>();

}