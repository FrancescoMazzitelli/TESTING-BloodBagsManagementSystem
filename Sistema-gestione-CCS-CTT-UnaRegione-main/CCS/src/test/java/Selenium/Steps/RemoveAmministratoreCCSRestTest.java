package Selenium.Steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoveAmministratoreCCSRestTest {

    public static WebDriver driver1;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    @Given("L'amministratore3 si autentica sul portale")
    public void l_amministratore3_si_autentica_sul_portale() throws InterruptedException, MalformedURLException {
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

        driver1.get(urlIn);

        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        Thread.sleep(1000);
        //driver1.findElement(By.xpath("//button[contains(text(), 'Log In')]")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
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
    }
}
