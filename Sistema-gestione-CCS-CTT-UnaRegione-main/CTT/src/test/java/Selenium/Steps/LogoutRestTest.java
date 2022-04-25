package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LogoutRestTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("L'utente si autentica sul portale")
    public void l_utente_si_autentica_sul_portale(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password3");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @When("L'utente si disconnette")
    public void l_utente_si_disconnette() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Log out')]")).click();
        Thread.sleep(1000);
        String url = driver1.getCurrentUrl();
        Assertions.assertEquals(url, urlIn);
        driver1.close();
    }

    @Then("Il logout non va a buon fine")
    public void il_logout_non_va_a_buon_fine() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver2 = new EdgeDriver();
        driver2.get(urlOut);
        Thread.sleep(500);
        String alert = driver2.switchTo().alert().getText();
        driver2.switchTo().alert().accept();
        //driver2.findElement(By.xpath("//button[contains(text(), 'Log out')]")).click();
        Thread.sleep(2000);
        System.out.println(alert);
        //driver2.switchTo().alert().accept();
        String alertChk = "E' necessario effettuare il login per accedere a questa risorsa";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver2.close();
    }
}
