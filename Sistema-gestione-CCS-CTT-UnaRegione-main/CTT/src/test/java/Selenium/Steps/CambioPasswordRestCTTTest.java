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

public class CambioPasswordRestCTTTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("Un amministratore si autentica sul portale")
    public void un_amministratore_si_autentica_sul_portale() throws MalformedURLException {
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
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password3");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
    }

    @When("viene inserita una nuova password")
    public void viene_inserita_una_nuova_password() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.id("chngpswd")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("newPass")).sendKeys("Password3");
        driver1.findElement(By.id("verifyPass")).sendKeys("Password3");
    }

    @Then("Viene sottomessa la nuova password")
    public void viene_sottomessa_la_nuova_password() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.id("runBtn")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "Password cambiata con successo";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Non viene sottomessa la nuova password a causa di un token errato")
    public void non_viene_sottomessa_la_nuova_password_a_causa_di_un_token_errato() throws InterruptedException, MalformedURLException {
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
        driver1.get("http://127.0.0.1:8081/ChangePasswordAfterLogin.html");
        driver1.findElement(By.id("newPass")).sendKeys("Password3");
        driver1.findElement(By.id("verifyPass")).sendKeys("Password3");
        boolean flag = false;
        driver1.findElement(By.id("runBtn")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        String alertChk = "Il token non è associato a nessun Dipendente";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @When("viene inserita una password errata")
    public void viene_inserita_una_password_errata() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.get(urlIn);
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password3");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Change Password')]")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("newPass")).sendKeys("passerrata");
        driver1.findElement(By.id("verifyPass")).sendKeys("passerrata");
    }

    @Then("viene sottomessa la nuova password ma non cambiata a causa del formato")
    public void viene_sottomessa_la_nuova_password_ma_non_cambiata_a_causa_del_formato() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.id("runBtn")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        String alertChk = "La password deve contenere almeno un numero, una lettera minuscola, una lettera Maiuscola e deve essere di almeno 8 caratteri";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Non viene sottomessa la nuova password poichè è specificato un codice fiscale diverso da quello dell'utente autenticato")
    public void non_viene_sottomessa_la_nuova_password_poichè_è_specificato_un_codice_fiscale_diverso_da_quello_dell_utente_autenticato() throws InterruptedException {
        boolean flag= false;
        driver1.get(urlIn);
        driver1.findElement(By.className("forgot")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("cdf")).sendKeys("SLNMPR46A28I321B");
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.xpath("//button[contains(text(), 'Cambia')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        String alertChk = "Username e codice fiscale non coincidono";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
