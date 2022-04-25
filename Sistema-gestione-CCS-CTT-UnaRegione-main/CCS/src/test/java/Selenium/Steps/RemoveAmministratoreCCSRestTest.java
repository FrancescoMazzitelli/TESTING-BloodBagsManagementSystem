package Selenium.Steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class RemoveAmministratoreCCSRestTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    @Given("L'amministratore3 si autentica sul portale")
    public void l_amministratore3_si_autentica_sul_portale() throws InterruptedException {
        driver1 = new EdgeDriver();
        driver1.get(urlIn);

        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Log In')]")).click();
    }

    @Then("Viene rimosso il dipendente selezionato")
    public void viene_rimosso_il_dipendente_selezionato() throws InterruptedException {
        Thread.sleep(1500);
        boolean flag = false;
        driver1.findElement(By.id("rimuoviAmm")).click();
        Thread.sleep(2000);
        driver1.findElement(By.id("codice")).findElement(By.xpath("//option[@value = 'SLNMJS46A28I123C']")).click();
        Thread.sleep(2000);
        driver1.findElement(By.id("conferma")).click();
        Thread.sleep(1500);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "Corretta rimozione del Dipendente: SLNMJS46A28I123C";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Non viene rimosso il dipendente selezionato poichè non presente nel db")
    public void non_viene_rimosso_il_dipendente_selezionato_poichè_non_presente_nel_db(){
        System.out.println("Non applicabile poichyè la GUI non consente di selezionare un utente non presente nel db");
    }

    @Then("Non viene rimosso il dipendente selezionato perchè non è possibile rimuovere se stessi")
    public void non_viene_rimosso_il_dipendente_selezionato_perchè_non_è_possibile_rimuovere_se_stessi(){
        System.out.println("Non applicabile poichyè la GUI non consente di selezionare ed eliminare se stessi");
        driver1.close();
    }
}
