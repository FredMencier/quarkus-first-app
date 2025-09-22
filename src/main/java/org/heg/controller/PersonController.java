package org.heg.controller;

import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.heg.entity.Person;
import org.heg.exception.UnknownPersonException;
import org.heg.manager.PersonManager;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.Collections;
import java.util.List;

@Path("")
public class PersonController {

    private final PersonManager personManager;

    public PersonController(PersonManager personManager) {
        this.personManager = personManager;
    }

    @GET
    @Path("/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPersons() {
        return personManager.findAllPerson();
    }

    @POST
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPerson(Person person) {
        personManager.insert(person);
    }

    @PUT
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    public Person updatePeson(Person person) throws UnknownPersonException {
        return personManager.update(person);
    }

    @DELETE
    @Path("/person/{personMail}")
    public void removePerson(@RestPath String personMail) {
        personManager.remove(personMail);
    }

    @GET
    @Path("/person/{personMail}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person retrivePerson(@RestHeader(value = "User-Agent") String userAgent, @RestPath String personMail) throws UnknownPersonException {
        Log.info("userAgent : " + userAgent);
        return personManager.findPersonByMail(personMail);
    }

    @GET
    @Path("/person/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPerson(@RestQuery String email, @RestQuery String name) throws UnknownPersonException {
        if (email != null && !email.isEmpty()) {
            return List.of(personManager.findPersonByMail(email));
        } else if (name != null && !name.isEmpty()) {
            return personManager.findByName(name);
        } else {
            return Collections.emptyList();
        }
    }
}
