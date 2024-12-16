package com.satellite.messenger.app.usecases;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/cucumber/features",
        glue = "com.satellite.messenger.app.cucumber.steps",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class RunCucumberTest {
}
