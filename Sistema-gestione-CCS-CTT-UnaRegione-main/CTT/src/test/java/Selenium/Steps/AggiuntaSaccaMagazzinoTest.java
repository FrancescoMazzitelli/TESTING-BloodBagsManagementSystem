package Selenium.Steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class AggiuntaSaccaMagazzinoTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("Viene compilato il form per l'accesso al portale del magazziniere")
    public void viene_compilato_il_form_per_l_accesso_al_portale_del_magazziniere(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @When("Viene compilato il form per l'aggiunta di una nuova sacca")
    public void viene_compilato_il_form_per_l_aggiunta_di_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        //driver1.findElement(By.id("aggSacc")).click();
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("21/04/2022");
        driver1.findElement(By.id("data_scadenza")).sendKeys("21/04/2025");
    }

    @Then("Viene sottomesso il form e creata una nuova sacca")
    public void viene_sottomesso_il_form_e_creata_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        String alertChk = "aggiunta correttamente";
        if(alert.contains(alertChk)) flag = true;
        Assertions.assertTrue(flag);

        //sacca 2
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("21/04/2022");
        driver1.findElement(By.id("data_scadenza")).sendKeys("21/04/2025");
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert2 = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert2);

        //sacca 3
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("21/04/2022");
        driver1.findElement(By.id("data_scadenza")).sendKeys("21/04/2025");
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert3 = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert3);

        driver1.close();
    }

    @When("Viene compilato il form errato per l'aggiunta di una nuova sacca")
    public void viene_compilato_il_form_errato_per_l_aggiunta_di_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Aggiunta sacca')]")).click();
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("21/04/2022");
        driver1.findElement(By.id("data_scadenza")).sendKeys("21/04/2021");
    }

    @Then("Viene sottomesso il form e non creata una nuova sacca")
    public void viene_sottomesso_il_form_e_non_creata_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        String alertChk = "La data di produzione non può essere precedente a quella di scadenza";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }
}
