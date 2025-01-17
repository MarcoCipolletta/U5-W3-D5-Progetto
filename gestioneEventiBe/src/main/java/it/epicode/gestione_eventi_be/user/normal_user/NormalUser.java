package it.epicode.gestione_eventi_be.user.normal_user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.epicode.gestione_eventi_be.reservation.Reservation;
import it.epicode.gestione_eventi_be.user.BasicUser;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class NormalUser extends BasicUser {

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();
}
