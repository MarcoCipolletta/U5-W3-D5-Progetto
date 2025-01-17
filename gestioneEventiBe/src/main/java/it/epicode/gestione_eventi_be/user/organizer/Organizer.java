package it.epicode.gestione_eventi_be.user.organizer;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.epicode.gestione_eventi_be.event.Event;
import it.epicode.gestione_eventi_be.user.BasicUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Organizer extends BasicUser {

    @OneToMany(mappedBy = "organizer")
    @ToString.Exclude
    @JsonManagedReference
    private List<Event> organizedEvents = new ArrayList<>();

}