package Selenium.Steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class AggiuntaSaccaMagazzinoTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    @Given("Viene compilato il form per l'accesso al portale del magazziniere")
    public void viene_compilato_il_form_per_l_accesso_al_portale_del_magazziniere() throws MalformedURLException {
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
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
    }

    @When("Viene compilato il form errato per l'aggiunta di una nuova sacca")
    public void viene_compilato_il_form_errato_per_l_aggiunta_di_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.id("aggSacc")).click();
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("");
        driver1.findElement(By.id("data_produzione")).sendKeys("");
        driver1.findElement(By.id("data_scadenza")).sendKeys("");
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("04/2015/21");
        driver1.findElement(By.id("data_scadenza")).sendKeys("04/2016/21");
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
        if(alert.equalsIgnoreCase(alertChk) || alert.equalsIgnoreCase("Impossibile aggiungere una sacca scaduta")) flag = true;
        Assertions.assertTrue(flag);
    }

    @When("Viene compilato il form per l'aggiunta di una nuova sacca")
    public void viene_compilato_il_form_per_l_aggiunta_di_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");

        //driver1.findElement(By.id("aggSacc")).click();
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("");
        driver1.findElement(By.id("data_produzione")).sendKeys("");
        driver1.findElement(By.id("data_scadenza")).sendKeys("");
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("04/2020/21");
        driver1.findElement(By.id("data_scadenza")).sendKeys("04/2025/21");
    }

    @Then("Viene sottomesso il form e creata una nuova sacca")
    public void viene_sottomesso_il_form_e_creata_una_nuova_sacca() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(2000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println("#############################################################################################################");
        System.out.println(alert);
        System.out.println("#############################################################################################################");
        String alertChk = "aggiunta correttamente";
        if(alert.contains(alertChk)) flag = true;
        Assertions.assertTrue(flag);

        //sacca 2
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium1");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("04/2020/21");
        driver1.findElement(By.id("data_scadenza")).sendKeys("04/2025/21");
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(2000);
        String alert2 = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println("#############################################################################################################");
        System.out.println(alert2);
        System.out.println("#############################################################################################################");
        if(alert2.contains(alertChk)) flag = true;
        Assertions.assertTrue(flag);

        //sacca 3
        Thread.sleep(1500);
        driver1.findElement(By.id("ente_donatore")).sendKeys("Selenium2");
        driver1.findElement(By.id("gruppo_sanguigno")).click();
        driver1.findElement(By.xpath("//option[@value = 'Ap']")).click();
        driver1.findElement(By.id("data_produzione")).sendKeys("04/2020/21");
        driver1.findElement(By.id("data_scadenza")).sendKeys("04/2025/21");
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(2000);
        String alert3 = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        System.out.println("#############################################################################################################");
        System.out.println(alert3);
        System.out.println("#############################################################################################################");
        if(alert3.contains(alertChk)) flag = true;
        Assertions.assertTrue(flag);

        driver1.close();
    }
}
