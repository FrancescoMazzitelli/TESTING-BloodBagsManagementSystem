package Selenium;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, dryRun = false, features="src/test/resources/Features", glue={"Selenium/Steps"}, monochrome = true, tags = "@Selenium")
public class TestRunner {
}
