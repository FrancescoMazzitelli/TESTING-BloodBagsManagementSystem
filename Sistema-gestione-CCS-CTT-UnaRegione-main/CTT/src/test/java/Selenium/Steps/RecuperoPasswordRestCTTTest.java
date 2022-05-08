package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RecuperoPasswordRestCTTTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("L'admin si autentica sul portale")
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
    }

    @When("Viene inserita una nuova password")
    public void viene_inserita_una_nuova_password() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.className("forgot")).click();
        Thread.sleep(1500);
        driver1.findElement(By.id("cdf")).sendKeys("SLNMJS46A28I123C");
        driver1.findElement(By.id("user")).sendKeys("seleniumMag");
    }

    @Then("Viene sottomesso il form e ottenuta una nuova password")
    public void viene_sottomesso_il_form_e_ottenuta_una_nuova_password() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.xpath("/html/body/div[2]/div/button[2]")).click();
        boolean flag = false;
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        if(alert != null) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Non viene sottomesso il form")
    public void non_viene_sottomesso_il_form() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.className("forgot")).click();
        Thread.sleep(1500);
        driver1.findElement(By.xpath("//*[@id='cdf']")).sendKeys("NNPRSN56B12I456P");
        driver1.findElement(By.id("user")).sendKeys("seleniumMag");

        String alertChk = "Il dipendente ricercatoNNPRSN56B12I456Pnon è stata trovato";
        boolean flag = false;
        driver1.findElement(By.xpath("/html/body/div[2]/div/button[2]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Non viene sottomesso il form per altro utente")
    public void non_viene_sottomesso_il_form_per_altro_utente() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//*[@id='cdf']")).sendKeys("SLNMPR46A28I321B");
        driver1.findElement(By.id("user")).sendKeys("seleniumMag");
        String alertChk = "Username e codice fiscale non coincidono";
        boolean flag = false;
        driver1.findElement(By.xpath("/html/body/div[2]/div/button[2]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert);
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
