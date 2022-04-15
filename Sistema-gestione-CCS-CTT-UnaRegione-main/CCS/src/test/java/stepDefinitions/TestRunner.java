package stepDefinitions;

import it.unisannio.ingegneriaDelSoftware.CcsRestApplication;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Features", glue={"stepDefinitions/Steps"}, monochrome = true)
public class TestRunner {
}
