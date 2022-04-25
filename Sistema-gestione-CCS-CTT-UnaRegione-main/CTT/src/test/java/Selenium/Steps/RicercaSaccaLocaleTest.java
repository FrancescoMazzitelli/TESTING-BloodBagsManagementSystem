package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class RicercaSaccaLocaleTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";
    String alertChk = "Confermare l'evasione?";
    String alertChk2 = "Localmente non è stata trovata nessuna sacca.";

    @Given("L'amministratore si logga sul portale")
    public void l_amministratore_si_logga_sul_portale(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("username 004");
        driver1.findElement(By.id("pass")).sendKeys("Password4");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @When("Viene compilato il form per la ricerca")
    public void viene_compilato_il_form_per_la_ricerca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div/div[1]/div/button")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("enteRic")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via dei Test 12");
        driver1.findElement(By.id("dataaff")).sendKeys("22/05/2023");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//*[@id='gruppo_sanguigno']/option[2]")).click();
        driver1.findElement(By.id("numero_sacche")).sendKeys("2");
    }

    @Then("Viene sottomesso il form e ricercata la sacca")
    public void viene_sottomesso_il_form_e_ricercata_la_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[3]/button")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        if(alert.contains(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @When("Viene compilato il form errato per la ricerca")
    public void viene_compilato_il_form_errato_per_la_ricerca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div/div[1]/div/button")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("enteRic")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via dei Test 12");
        driver1.findElement(By.id("dataaff")).sendKeys("22/05/2023");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//*[@id='gruppo_sanguigno']/option[9]")).click();
        driver1.findElement(By.id("numero_sacche")).sendKeys("2");
    }

    @Then("Viene sottomesso il form e non ricercata la sacca")
    public void viene_sottomesso_il_form_e_non_ricercata_la_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[3]/button")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        if(alert.contains(alertChk2)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }


}
