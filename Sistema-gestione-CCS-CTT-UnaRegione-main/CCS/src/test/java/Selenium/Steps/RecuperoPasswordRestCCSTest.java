package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class RecuperoPasswordRestCCSTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    @Given("Viene immesso l'username")
    public void viene_immesso_l_username() throws InterruptedException {
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//a[contains(text(), 'Hai dimenticato email o password?')]")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("cdf")).sendKeys("SLNMJS46A28I123C");
        driver1.findElement(By.id("user")).sendKeys("selenium");
    }

    @Then("Viene sottomesso l'username e recuperata la password")
    public void viene_sottomesso_l_username_e_recuperata_la_password() throws InterruptedException {
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Cambia')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "La tua nuova password è: ";
        if(alert.contains(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Given("Viene immesso l'username di un utente presente il cui codice fiscale è errato")
    public void viene_immesso_l_username_di_un_utente_presente_il_cui_codice_fiscale_è_errato() throws InterruptedException {
        //driver1.get(urlIn);
        Thread.sleep(1000);
        urlOut = driver1.getCurrentUrl();
        driver1.findElement(By.xpath("//a[contains(text(), 'Hai dimenticato email o password?')]")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("cdf")).sendKeys("KTMFSW67T64I460F");
        driver1.findElement(By.id("user")).sendKeys("selenium");
    }

    @Then("Viene sottomessso l'username e non recuperata la password")
    public void viene_sottomessso_l_username_e_non_recuperata_la_password() throws InterruptedException {
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Cambia')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "username e codice fiscale non coincidono";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Given("Viene immesso l'username di un utente presente nel db ma viene associato al CF di un altro utente")
    public void viene_immesso_l_username_di_un_utente_presente_nel_db_ma_viene_associato_al_CF_di_un_altro_utente() throws InterruptedException {
        //driver1.get(urlOut);
        //Thread.sleep(1000);
        //driver1.findElement(By.xpath("//a[contains(text(), 'Hai dimenticato email o password?')]")).click();
        //driver1.findElement(By.className("forgot")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("cdf")).sendKeys("KTMFSW67T64I460F");
        driver1.findElement(By.id("user")).sendKeys("pippoPluto");
    }

    @Then("Viene sottomessso l'username e non recuperata la password perchè il CF non corrisponde")
    public void viene_sottomessso_l_username_e_non_recuperata_la_password_perchè_il_CF_non_corrisponde() throws InterruptedException {
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Cambia')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        String alertChk = "username e codice fiscale non coincidono";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
