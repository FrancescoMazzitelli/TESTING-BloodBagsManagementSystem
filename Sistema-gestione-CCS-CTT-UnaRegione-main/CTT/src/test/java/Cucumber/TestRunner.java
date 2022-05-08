package Cucumber;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, dryRun = false, features="src/test/resources/Features", glue={"Cucumber/Steps"}, monochrome = true, tags = "@Cucumber")
public class TestRunner {
}
