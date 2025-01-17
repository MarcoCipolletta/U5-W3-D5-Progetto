package it.epicode.gestione_eventi_be.user;

import it.epicode.gestione_eventi_be.auth.AppUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "basic_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BasicUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private AppUser appUser;


}
