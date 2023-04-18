package com.prueba.controller;

import com.prueba.model.Division;
import org.eclipse.microprofile.faulttolerance.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Path("/divisions")
@Produces(MediaType.APPLICATION_JSON)
public class DivisionController {
    List<Division> divisions = new ArrayList<>();
    Long id = 0L;
    Logger LOGGER = Logger.getLogger("Demologger");

    @GET
    @Bulkhead(value = 5)
    @Timeout(value = 5000L)
    @Fallback(fallbackMethod = "getTimeoutFallbackList")
    public List<Division> getDivisionList() {
        LOGGER.info("Ejecutando retorno de divisiones");
        doWait();
        return this.divisions;
    }

    public void doWait() {
        var random = new Random();
        try {
            LOGGER.warning("Haciendo un sleep");
            Thread.sleep((random.nextInt(10) + 2) * 1000L);
        } catch (Exception ex){

        }
    }

    public List<Division> getTimeoutFallbackList(){
        var division = new Division(-2L, -2, -2, -2);
        return List.of(division);
    }

    @POST
    @Retry(maxRetries = 3)
    @Bulkhead(value = 1)
    @CircuitBreaker(failureRatio = 0.1, delay = 15000L)
    @Fallback(fallbackMethod = "getDivisionFallbackList")
    public Division generate() {
        LOGGER.info("Ejecutando generacion de divisiones");
        int number1 = (int)(Math.random()*11);
        int number2 = (int)(Math.random()*11);
        if (number2 == 0) {
            LOGGER.warning("Se produce una falla");
            throw new RuntimeException("Division entre 0");
        } else {
            float result = number1 / number2;
            Division aux = new Division(this.id, number1, number2, result);
            this.id += 1;
            divisions.add(aux);
            return aux;
        }
    }

    public Division getDivisionFallbackList(){
        var division = new Division(-1L, -1, -1, -1);
        return division;
    }

}
