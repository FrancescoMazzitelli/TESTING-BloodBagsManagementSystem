package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LogoutTest {

    WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    @Given("L'admin si autentica tramite form")
    public void l_admin_si_autentica_tramite_form(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @Then("Viene rimosso il token generato dal il login precedente")
    public void viene_rimosso_il_token_generato_dal_il_login_precedente() throws InterruptedException {
        Thread.sleep(1000);
        //driver1.findElement(By.cssSelector("button[class='btn btn-primary' onclick='logout();' type='button']")).click();
        driver1.findElement(By.xpath("//button[contains(text(),'Log out')]")).click();
        String current = driver1.getCurrentUrl();
        boolean flag = false;
        if(current.equalsIgnoreCase(urlIn)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
