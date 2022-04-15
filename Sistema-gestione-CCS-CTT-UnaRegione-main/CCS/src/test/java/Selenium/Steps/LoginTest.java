package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.asynchttpclient.proxy.ProxyServer;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.IOException;


public class LoginTest {

    WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    @Given("Viene compilato il form per il login")
    public void viene_compilato_il_form_per_il_login() {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
    }

    @Then("Viene sottomesso il form e garantito l'accesso")
    public void viene_sottomesso_il_form_e_garantito_l_accesso() throws IOException, InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        driver1.findElement(By.id("btnLogin")).click();

        Thread.sleep(1000);
        String current = driver1.getCurrentUrl();
        Assertions.assertEquals(current, urlOut);
        driver1.close();
    }

    @Given("Viene compilato il form per il login di un admin non presente")
    public void viene_compilato_il_form_per_il_login_di_un_admin_non_presente(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        driver2 = new EdgeDriver();
        driver2.get(urlIn);
        driver2.manage().window().maximize();
        driver2.findElement(By.id("user")).sendKeys("admiN");
        driver2.findElement(By.id("pass")).sendKeys("Admin");
    }

    @Then("Viene sottomesso il form e non garantito l'accesso")
    public void viene_sottomesso_il_form_e_non_garantito_l_accesso() throws IOException, InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        driver2.findElement(By.id("btnLogin")).click();

        Thread.sleep(1000);
        String current = driver2.getCurrentUrl();
        String message = driver2.findElement(By.cssSelector("span[id='error']")).getText();
        String check = "Impossibile trovare il dipendente. Username o Password errati";
        boolean flag = false;
        if(current.equalsIgnoreCase(urlIn) && message.equalsIgnoreCase(check)) flag = true;
        Assertions.assertTrue(flag);

        driver2.close();
    }


}
