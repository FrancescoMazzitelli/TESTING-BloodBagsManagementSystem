package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class CambioPasswordRestCCSTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/ChangePasswordAfterLogin.html";

    @Given("L'utente si autentica sull'apposito portale tramite form admin1")
    public void l_utente_si_autentica_sull_apposito_portale_tramite_form_admin1(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @When("Viene inserita la nuova password")
    public void viene_inserita_la_nuova_password() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        Thread.sleep(2000);
        driver1.findElement(By.id("chngpsswrd")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("newPass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("verifyPass")).sendKeys("Adminadmin1");

    }

    @Then("Viene sottomessa e cambiata la password")
    public void viene_sottomessa_e_cambiata_la_password() throws InterruptedException {
        driver1.findElement(By.id("runBtn")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "Password cambiata con successo";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
    }

    @Then("Viene sottomessa e non cambiata la password a causa del token errato")
    public void viene_sottomessa_e_non_cambiata_la_password_a_causa_del_token_errato() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        driver2 = new EdgeDriver();
        driver2.get(urlOut);
        driver2.manage().window().maximize();
        Thread.sleep(1000);

        driver2.findElement(By.id("newPass")).sendKeys("Adminadmin1");
        driver2.findElement(By.id("verifyPass")).sendKeys("Adminadmin1");

        driver2.findElement(By.id("runBtn")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver2.switchTo().alert();
        String alertMex = driver2.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "Il token non è associato a nessun Dipendente";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
        driver2.close();
    }

    @When("Viene inserita la nuova password errata")
    public void viene_inserita_la_nuova_password_errata() throws InterruptedException {
        driver1.get(urlOut);

        Thread.sleep(1000);
        driver1.findElement(By.id("newPass")).sendKeys("admin");
        driver1.findElement(By.id("verifyPass")).sendKeys("admin");
    }

    @Then("Viene sottomessa e non cambiata la password a causa del suo formato")
    public void viene_sottomessa_e_non_cambiata_la_password_a_causa_del_suo_formato() throws InterruptedException {
        driver1.findElement(By.id("runBtn")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "La password deve contenere almeno un numero, deve essere di 8 caratteri, ed avere almeno una lettera grande";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
    }

    @Then("Viene sottomessa e non cambiata la password perchè il CF inserito non è lo stesso dell'utente autenticato")
    public void  viene_sottomessa_e_non_cambiata_la_password_perchè_il_CF_inserito_non_è_lo_stesso_dell_utente_autenticato() throws InterruptedException {
        boolean flag = false;
        driver1. get("http://127.0.0.1:8080/ChangePassword.html");
        Thread.sleep(1000);

        driver1.findElement(By.id("cdf")).sendKeys("FZDTSS79C20F641W");
        driver1.findElement(By.id("user")).sendKeys("admin");

        driver1.findElement(By.xpath("//button[contains(text(), 'Cambia')]")).click();
        Thread.sleep(1000);

        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();

        String alertChk = "username e codice fiscale non coincidono";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
