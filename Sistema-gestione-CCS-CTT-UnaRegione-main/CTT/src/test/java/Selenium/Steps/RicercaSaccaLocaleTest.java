package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RicercaSaccaLocaleTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";
    String alertChk = "La richiesta non è stata soddisfatta completamente in locale.\n" +
            "CCS è offline quindi non è possibile effettuare una ricerca GLOBALE.\n" +
            "Se conferma l'evasione verrà notificato il magazziniere di evadere comunque le sacche trovate in locale";
    String alertChk1 = "Confermare l'evasione?";
    String alertChk2 = "Localmente non è stata trovata nessuna sacca.";

    @Given("L'amministratore si logga sul portale")
    public void l_amministratore_si_logga_sul_portale() throws MalformedURLException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();

        //WebDriverManager.edgedriver().setup();

        options.addArguments("test-type");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.setCapability("platform", Platform.LINUX);

        //URL remoteUrl = new URL("http://192.168.1.125:4444/wd/hub");
        URL remoteUrl = new URL("http://172.17.0.2:4444/wd/hub");

        driver1 = new RemoteWebDriver(remoteUrl, options);
        driver1.get(urlIn);
        driver1.findElement(By.id("user")).sendKeys("username 004");
        driver1.findElement(By.id("pass")).sendKeys("Password4");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
    }

    @When("Viene compilato il form per la ricerca")
    public void viene_compilato_il_form_per_la_ricerca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/div/div[1]/div/button")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("enteRic")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via dei Test 12");
        driver1.findElement(By.id("dataaff")).sendKeys("01/2025/22");
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
        driver1.switchTo().alert().dismiss();
        System.out.println(alert);
        if(alert.contains(alertChk) || alert.contains(alertChk1) || alert.contains(alertChk2)) flag = true;
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
        driver1.findElement(By.id("dataaff")).sendKeys("22/01/2022");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//*[@id='gruppo_sanguigno']/option[9]")).click();
        driver1.findElement(By.id("numero_sacche")).sendKeys("2");
    }

    @Then("Viene sottomesso il form e non ricercata la sacca")
    public void viene_sottomesso_il_form_e_non_ricercata_la_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[3]/button")).click();
        Thread.sleep(2000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        if(alert.contains(alertChk2) || alert.equalsIgnoreCase("Data affidamento inserita non valida.")) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }


}
