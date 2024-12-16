package com.satellite.messenger.app.usecases.steps;

import com.satellite.messenger.app.dto.Circle;
import com.satellite.messenger.app.exceptions.location.CircleWithinException;
import com.satellite.messenger.app.exceptions.location.EqualCircleException;
import com.satellite.messenger.app.exceptions.location.NotIntersectionException;
import com.satellite.messenger.app.utils.LocationUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LocationErrorManaging {

    private Exception exception;

    private void setException(
            final Circle circleA,
            final Circle circleB,
            final Circle circleC
    ) {
        try {
            LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
        } catch (Exception e) { exception = e; }
    }

    @When("Pruebo con un caso de no intersección en los dos primeros satelites")
    public void pruebo_con_un_caso_de_no_intersección_en_los_dos_primeros_satelites() {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 200);
        final Circle circleB = new Circle((double) -240, (double) -450, 115.5);
        final Circle circleC = new Circle((double) 170, (double) -300, (double) 250);
        setException(circleA, circleB, circleC);
    }

    @When("Pruebo con un caso de no intersección en el tercer satelite")
    public void pruebo_con_un_caso_de_no_intersección_en_el_tercer_satelite() {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleB = new Circle((double) 100, (double) -100, 115.5);
        final Circle circleC = new Circle((double) 170, (double) -300, (double) 250);
        setException(circleA, circleB, circleC);
    }

    @Then("El resultado debería ser una excepción de tipo no-intersection")
    public void el_resultado_debería_ser_una_excepción_de_tipo_no_intersection() {
        Assert.assertEquals(exception.getClass(), NotIntersectionException.class);
    }

    @When("Pruebo con un caso donde un círculo está dentro de otro")
    public void pruebo_con_un_caso_donde_un_círculo_está_dentro_de_otro() {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleB = new Circle((double) -240, (double) -450, 115.5);
        final Circle circleC = new Circle((double) 170, (double) -300, (double) 250);
        setException(circleA, circleB, circleC);
    }

    @Then("El resultado debería ser una excepción de tipo circle-within")
    public void el_resultado_debería_ser_una_excepción_de_tipo_circle_within() {
        Assert.assertEquals(exception.getClass(), CircleWithinException.class);
    }

    @When("Pruebo con un caso donde primer círculo es igual al segundo")
    public void pruebo_con_un_caso_donde_primer_círculo_es_igual_al_segundo() {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleB = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleC = new Circle((double) 170, (double) -300, (double) 250);
        setException(circleA, circleB, circleC);
    }

    @When("Pruebo con un caso donde primer círculo es igual al tercero")
    public void pruebo_con_un_caso_donde_primer_círculo_es_igual_al_tercero() {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleB = new Circle((double) 170, (double) -300, (double) 250);
        final Circle circleC = new Circle((double) -500, (double) -200, (double) 600);
        setException(circleA, circleB, circleC);
    }

    @Then("El resultado debería ser una excepción de tipo equal-circle")
    public void el_resultado_debería_ser_una_excepción_de_tipo_equal_circle() {
        Assert.assertEquals(exception.getClass(), EqualCircleException.class);
    }
}