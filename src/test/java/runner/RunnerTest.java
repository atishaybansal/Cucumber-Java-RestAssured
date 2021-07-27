package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

	
	@RunWith(Cucumber.class)
	@CucumberOptions(
	        features="src/test/resources/featurefile",
	        glue="stepDefinition",
	        plugin={"json:target/cucumber.json","pretty","html:target/cucumber-reports"}
	)

	public class RunnerTest {

	}

