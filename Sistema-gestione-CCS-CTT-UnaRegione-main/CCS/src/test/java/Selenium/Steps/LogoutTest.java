package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class LogoutTest {

    public static WebDriver driver1 ;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    @Given("L'admin si autentica tramite form")
    public void l_admin_si_autentica_tramite_form() throws MalformedURLException {
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

        URL remoteUrl = new URL("http://localhost:4444/wd/hub");

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

    @Then("Viene rimosso il token generato dal il login precedente")
    public void viene_rimosso_il_token_generato_dal_il_login_precedente() throws InterruptedException {
        Thread.sleep(1000);
        //driver1.findElement(By.cssSelector("button[class='btn btn-primary' onclick='logout();' type='button']")).click();
        driver1.findElement(By.xpath("//button[contains(text(),'Log out')]")).click();
        Thread.sleep(1000);
        String current = driver1.getCurrentUrl();
        boolean flag = false;
        if(current.equalsIgnoreCase(urlIn)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Non viene rimosso il token poichè è errato")
    public void non_viene_rimosso_il_token_poichè_è_errato() throws InterruptedException {

        boolean flag = false;
        driver1.get(urlOut);
        Thread.sleep(2000);
        String alert = driver1.switchTo().alert().getText();
        //driver2.findElement(By.xpath("//button[contains(text(),'Log out')]")).click();
        driver1.switchTo().alert().accept();
        Thread.sleep(2000);
        String alertChk = "E' necessario effettuare il login per accedere a questa risorsa";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }
}
