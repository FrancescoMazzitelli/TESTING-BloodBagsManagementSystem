package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class AggiungiCTTRestTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";
    String urlAgg = "http://127.0.0.1:8080/AggiuntaNuovoCTTForm.html";

    @Given("L'amministratore accede al portale tramite form")
    public void l_amministratore_accede_al_portale_tramite_form(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//button[contains(text(),'Aggiunta nuovo CTT')]")).click();

        driver1.findElement(By.id("provincia")).sendKeys("BN");
        driver1.findElement(By.id("citta")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via Selenium 12");
        driver1.findElement(By.id("telefono")).sendKeys("1234568790");
        driver1.findElement(By.id("mail")).sendKeys("selenium@gmail.com");
        driver1.findElement(By.id("latitude")).sendKeys("45");
        driver1.findElement(By.id("longitude")).sendKeys("100");
    }

    @Then("Viene sottomesso il form e creato un nuovo CTT")
    public void viene_sottomesso_il_form_e_creato_un_nuovo_CTT() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.xpath("//button[contains(text(),'Conferma')]")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();
        System.out.println(alertMex);
        String alertChk = "CTT001 aggiunto correttamente";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
    }

    //Non è possibile impostare numero e nome direttamente dall GUI quindi per simulare un comportamento simile non verrà inserito il campo "città" e "provincia"
    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, numero e nome")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_sbagliato_numero_e_nome() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);

        driver1.findElement(By.id("provincia")).sendKeys("");
        driver1.findElement(By.id("citta")).sendKeys("");
        driver1.findElement(By.id("indirizzo")).sendKeys("");
        driver1.findElement(By.id("telefono")).sendKeys("");
        driver1.findElement(By.id("mail")).sendKeys("");
        driver1.findElement(By.id("latitude")).sendKeys("");
        driver1.findElement(By.id("longitude")).sendKeys("");

        driver1.findElement(By.id("indirizzo")).sendKeys("via Selenium 12");
        driver1.findElement(By.id("telefono")).sendKeys("1234568790");
        driver1.findElement(By.id("mail")).sendKeys("selenium@gmail.com");
        driver1.findElement(By.id("latitude")).sendKeys("45");
        driver1.findElement(By.id("longitude")).sendKeys("100");

    }

    @Then("Viene sottomesso il form e non viene creato un nuovo CTT a causa del numero e nome CTT")
    public void viene_sottomesso_il_form_e_non_viene_creato_un_nuovo_CTT_a_causa_del_numero_e_nome_CTT() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.xpath("//button[contains(text(),'Conferma')]")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "La provincia deve essere di due caratteri";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, telefono")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_sbagliato_telefono() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(2000);

        driver1.findElement(By.id("provincia")).sendKeys("");
        driver1.findElement(By.id("citta")).sendKeys("");
        driver1.findElement(By.id("indirizzo")).sendKeys("");
        driver1.findElement(By.id("telefono")).sendKeys("");
        driver1.findElement(By.id("mail")).sendKeys("");
        driver1.findElement(By.id("latitude")).sendKeys("");
        driver1.findElement(By.id("longitude")).sendKeys("");

        driver1.findElement(By.id("provincia")).sendKeys("BN");
        driver1.findElement(By.id("citta")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via Selenium 12");
        driver1.findElement(By.id("telefono")).sendKeys("1234568");
        driver1.findElement(By.id("mail")).sendKeys("selenium@gmail.com");
        driver1.findElement(By.id("latitude")).sendKeys("45");
        driver1.findElement(By.id("longitude")).sendKeys("100");
    }

    @Then("Viene sottomesso il form e non viene creato un nuovo CTT a causa del telefono")
    public void viene_sottomesso_il_form_e_non_viene_creato_un_nuovo_CTT_a_causa_del_telefono() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.xpath("//button[contains(text(),'Conferma')]")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "Formato del numero di telefono non valido";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, latitudine")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_sbagliato_latitudine() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(2000);

        driver1.findElement(By.id("provincia")).sendKeys("");
        driver1.findElement(By.id("citta")).sendKeys("");
        driver1.findElement(By.id("indirizzo")).sendKeys("");
        driver1.findElement(By.id("telefono")).sendKeys("");
        driver1.findElement(By.id("mail")).sendKeys("");
        driver1.findElement(By.id("latitude")).sendKeys("");
        driver1.findElement(By.id("longitude")).sendKeys("");

        driver1.findElement(By.id("provincia")).sendKeys("BN");
        driver1.findElement(By.id("citta")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via Selenium 12");
        driver1.findElement(By.id("telefono")).sendKeys("1234568");
        driver1.findElement(By.id("mail")).sendKeys("selenium@gmail.com");
        driver1.findElement(By.id("latitude")).sendKeys("780");
        driver1.findElement(By.id("longitude")).sendKeys("100");
    }

    @Then("Viene sottomesso il form e non viene creato un nuovo CTT a causa della latitudine")
    public void viene_sottomesso_il_form_e_non_viene_creato_un_nuovo_CTT_a_causa_della_latitudine() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.xpath("//button[contains(text(),'Conferma')]")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "empty String";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
        driver1.close();
    }
}
