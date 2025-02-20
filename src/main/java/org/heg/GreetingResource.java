package org.heg;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("Receive request on /hello");
        return "Hello RESTEasy";
    }

    @GET
    @Path("bonjour")
    @Produces(MediaType.TEXT_PLAIN)
    public String bonjour() {
        Log.info("mon log");
        Log.debug("debug message");
        return "Bonjour RESTEasy";
    }
}
