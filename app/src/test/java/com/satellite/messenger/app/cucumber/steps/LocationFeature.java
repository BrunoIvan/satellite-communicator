package com.satellite.messenger.app.cucumber.steps;

import com.satellite.messenger.app.dto.Circle;
import com.satellite.messenger.app.dto.Locable;
import com.satellite.messenger.app.utils.LocationUtils;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class LocationFeature {

    protected Locable result;

    @When("Pruebo con un caso feliz")
    public void pruebo_con_un_caso_feliz() {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleB = new Circle((double) 100, (double) -100, 115.5);
        final Circle circleC = new Circle((double) 170, (double) -300, 109.856054);
        result = LocationUtils.intersectThreeCircles(circleA, circleB, circleC);
    }

    @When("Pruebo con un caso feliz con posiciones predeterminadas")
    public void pruebo_con_un_caso_feliz_con_posiciones_predeterminadas() {
        result = LocationUtils.intersectThreeCircles(600, 115.5, 109.856054);
    }

    @Then("El resultado debería ser: x: {double} y: {double}")
    public void el_resultado_debería_ser_x_y(double x, double y) {
        Assert.assertEquals(result.getX(), (Double)  x);
        Assert.assertEquals(result.getY(), (Double)  y);
    }

    @When("Pruebo con un caso feliz con redondeo de {int}")
    public void pruebo_con_un_caso_feliz_con_redondeo_de(int round) {
        final Circle circleA = new Circle((double) -500, (double) -200, (double) 600);
        final Circle circleB = new Circle((double) 100, (double) -100, 115.5);
        final Circle circleC = new Circle((double) 170, (double) -300, 109.856054);
        result = LocationUtils.intersectThreeCircles(circleA, circleB, circleC, round);
    }
}