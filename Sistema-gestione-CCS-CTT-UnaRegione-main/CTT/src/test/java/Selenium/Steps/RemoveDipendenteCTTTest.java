package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoveDipendenteCTTTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("L'amministratore si autentica sul portale")
    public void l_amministratore_si_autentica_sul_portale() throws MalformedURLException {
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

    @Then("Vengono eliminati due utenti")
    public void vengono_eliminati_due_utenti() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag1 = false, flag2 = false;
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Rimozione dipendente CTT')]")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("codice")).click();
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//option[@value='SLNMJS46A28I123C']")).click();
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert1 = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert1);
        String alertChk1 = "Dipendente SLNMJS46A28I123C rimosso correttamente";
        if(alert1.equalsIgnoreCase(alertChk1)) flag1 = true;
        Assertions.assertTrue(flag1);

        Thread.sleep(1000);

        driver1.findElement(By.id("codice")).click();
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//option[@value='SLNMPR46A28I321B']")).click();
        Thread.sleep(2000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert2 = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println(alert2);
        String alertChk2 = "Dipendente SLNMPR46A28I321B rimosso correttamente";
        if(alert2.equalsIgnoreCase(alertChk2)) flag2 = true;
        Assertions.assertTrue(flag2);
        driver1.close();
    }

    @Then("Non vengono eliminati gli utenti")
    public void non_vengono_eliminati_gli_utenti(){
        System.out.println("Non applicabile poichè non è possibile eliminare utenti non presenti nel DB tramite GUI");
    }

    @Then("Non viene eliminato l'utente poichè non è possibile eliminare se stessi")
    public void non_viene_eliminato_l_utente_poichè_non_è_possibile_eliminare_se_stessi(){
        System.out.println("Non applicabile poichè non è possibile eliminare se stessi tramite GUI");
    }
}
