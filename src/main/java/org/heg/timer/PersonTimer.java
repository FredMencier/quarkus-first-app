package org.heg.timer;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import org.heg.manager.PersonManager;

@ApplicationScoped
public class PersonTimer {

    final PersonManager personManager;

    public PersonTimer(PersonManager personManager) {
        this.personManager = personManager;
    }

    @Scheduled(every = "10s")
    public void printNbPerson() {
        int nbPerson = personManager.findAllPerson().size();
        Log.info("Actuellement il y a %s personnes dans la base de données".formatted(nbPerson));
    }
}
