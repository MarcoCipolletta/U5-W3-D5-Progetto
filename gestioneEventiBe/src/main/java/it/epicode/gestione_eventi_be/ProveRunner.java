package it.epicode.gestione_eventi_be;


import it.epicode.gestione_eventi_be.user.organizer.OrganizerSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(10)
public class ProveRunner implements ApplicationRunner {
private final OrganizerSvc organizerSvc;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        System.out.println(organizerSvc.findByAppUser("organizer"));
    }
}