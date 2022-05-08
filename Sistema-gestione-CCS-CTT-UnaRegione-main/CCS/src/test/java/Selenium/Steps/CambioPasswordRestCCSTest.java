package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class CambioPasswordRestCCSTest {

    public static WebDriver driver1;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlHub = "http://127.0.0.1:8080/AmministratoreCCS.html";
    String urlOut = "http://127.0.0.1:8080/ChangePasswordAfterLogin.html";

    @Given("L'utente si autentica sull'apposito portale tramite form admin1")
    public void l_utente_si_autentica_sull_apposito_portale_tramite_form_admin1() throws MalformedURLException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();

        //WebDriverManager.edgedriver().setup();

        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName("MicrosoftEdge");

        options.addArguments("test-type");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.setCapability("platform", Platform.LINUX);

        //URL remoteUrl = new URL("http://localhost:4444/wd/hub");
        URL remoteUrl = new URL("http://172.17.0.2:4444/wd/hub");

        driver1 = new RemoteWebDriver(remoteUrl, options);

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        //driver1.findElement(By.id("btnLogin")).click();

        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
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
        driver1.close();
    }

    @Then("Viene sottomessa e non cambiata la password a causa del token errato")
    public void viene_sottomessa_e_non_cambiata_la_password_a_causa_del_token_errato() throws InterruptedException, MalformedURLException {
        //System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
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

        //URL remoteUrl = new URL("http://localhost:4444/wd/hub");
        URL remoteUrl = new URL("http://172.17.0.2:4444/wd/hub");

        driver1 = new RemoteWebDriver(remoteUrl, options);

        driver1.get(urlOut);

        driver1.findElement(By.id("newPass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("verifyPass")).sendKeys("Adminadmin1");

        driver1.findElement(By.id("runBtn")).click();

        boolean flag = false;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();

        alert.accept();

        System.out.println(alertMex);
        String alertChk = "Il token non è associato a nessun Dipendente";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = true;

        Assertions.assertTrue(flag);
    }

    @When("Viene inserita la nuova password errata")
    public void viene_inserita_la_nuova_password_errata() throws InterruptedException {

        Thread.sleep(2000);

        driver1.get(urlIn);
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        //driver1.findElement(By.id("btnLogin")).click();

        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);

        Thread.sleep(2000);
        driver1.findElement(By.id("chngpsswrd")).click();
        Thread.sleep(1000);

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
