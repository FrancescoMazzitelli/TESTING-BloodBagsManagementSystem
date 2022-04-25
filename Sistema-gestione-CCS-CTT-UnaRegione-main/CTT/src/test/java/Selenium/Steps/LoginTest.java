package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("Viene compilato il form per l'accesso di un dipendente corretto")
    public void viene_compilato_il_form_per_l_accesso_di_un_dipendente_corretto(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password3");
    }

    @Then("Viene sottomesso il form ed effettuato l'accesso")
    public void viene_sottomesso_il_form_ed_effettuato_l_accesso() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.id("btnLogin")).click();
        Thread.sleep(1000);
        String url = driver1.getCurrentUrl();
        Assertions.assertEquals(url, urlOut);
    }

    @Given("Viene compilato un form errato per l'accesso di un dipendente")
    public void viene_compilato_un_form_errato_per_l_accesso_di_un_dipendente(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.get(urlIn);
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password1");
    }

    @Then("Viene sottomesso il form e non effettuato l'accesso")
    public void viene_sottomesso_il_form_e_non_effettuato_l_accesso() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.id("btnLogin")).click();
        Thread.sleep(1000);
        String url = driver1.getCurrentUrl();
        Assertions.assertEquals(url, urlIn);
        driver1.close();
    }
}
