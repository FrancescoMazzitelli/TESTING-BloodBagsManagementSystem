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

public class LogoutRestTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("L'utente si autentica sul portale")
    public void l_utente_si_autentica_sul_portale() throws MalformedURLException {
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

    @When("L'utente si disconnette")
    public void l_utente_si_disconnette() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Log out')]")).click();
        Thread.sleep(1000);
        String url = driver1.getCurrentUrl();
        Assertions.assertEquals(url, urlIn);
    }

    @Then("Il logout non va a buon fine")
    public void il_logout_non_va_a_buon_fine() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.get(urlOut);
        Thread.sleep(500);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        //driver1.findElement(By.xpath("//button[contains(text(), 'Log out')]")).click();
        Thread.sleep(2000);
        System.out.println(alert);
        //driver1.switchTo().alert().accept();
        String alertChk = "E' necessario effettuare il login per accedere a questa risorsa";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
