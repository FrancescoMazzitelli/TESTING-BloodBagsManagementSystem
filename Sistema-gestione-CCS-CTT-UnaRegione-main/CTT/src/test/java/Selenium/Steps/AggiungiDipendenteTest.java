package Selenium.Steps;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.CttRestApplication;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.mongodb.client.model.Filters.eq;


public class AggiungiDipendenteTest {

    private MongoDataManager md = MongoDataManager.getInstance();

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

/*
    @Given("BeforeAll")
    public void BeforeAll() throws InterruptedException {
        CttRestApplication.main();
    }
*/
    @Given("L'utente effettua l'accesso sull'apposito portale")
    public void l_utente_effettua_l_accesso_sull_apposito_portale() throws MalformedURLException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        MongoDataManager md = MongoDataManager.getInstance();
        Bson query1, query2, query3, query4, query5, query6, query7, query8, query9;
        query1 = eq("seriale", "CTT001-00000001");
        query2 = eq("seriale", "CTT001-00000002");
        query3 = eq("seriale", "CTT001-00000003");
        query4 = eq("seriale", "CTT001-00000011");
        query5 = eq("seriale", "CTT001-00000012");
        query6 = eq("seriale", "CTT001-00000013");
        query7 = eq("seriale", "CTT001-00000008");
        query8 = eq("seriale", "CTT001-00000009");
        query9 = eq("seriale", "CTT001-00000010");
        MongoCollection collection = md.getCollectionDatiSacca();
        DeleteResult result1 = collection.deleteOne(query1);
        DeleteResult result2 = collection.deleteOne(query2);
        DeleteResult result3 = collection.deleteOne(query3);
        DeleteResult result4 = collection.deleteOne(query4);
        DeleteResult result5 = collection.deleteOne(query5);
        DeleteResult result6 = collection.deleteOne(query6);
        DeleteResult result7 = collection.deleteOne(query7);
        DeleteResult result8 = collection.deleteOne(query8);
        DeleteResult result9 = collection.deleteOne(query9);

        System.out.println("CTT001-00000001 "+result1.wasAcknowledged());
        System.out.println("CTT001-00000002 "+result2.wasAcknowledged());
        System.out.println("CTT001-00000003 "+result3.wasAcknowledged());
        System.out.println("CTT001-00000011 "+result4.wasAcknowledged());
        System.out.println("CTT001-00000012 "+result5.wasAcknowledged());
        System.out.println("CTT001-00000013 "+result6.wasAcknowledged());
        System.out.println("CTT001-00000008 "+result7.wasAcknowledged());
        System.out.println("CTT001-00000009 "+result8.wasAcknowledged());
        System.out.println("CTT001-00000010 "+result9.wasAcknowledged());

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

    @When("Viene compilato il form per l'aggiunta di un magazziniere")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_magazziniere() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1500);
        driver1.findElement(By.id("aggDip")).click();
        Thread.sleep(1000);

        driver1.findElement(By.id("codice")).sendKeys("SLNMJS46A28I123C");
        driver1.findElement(By.id("nome")).sendKeys("SeleniumMag");
        driver1.findElement(By.id("cognome")).sendKeys("TesterMag");
        driver1.findElement(By.id("datanasc")).sendKeys("12/10/1999");
        driver1.findElement(By.id("username")).sendKeys("seleniumMag");
        driver1.findElement(By.id("rl")).click();
        driver1.findElement(By.xpath("//option[@value = 'MagazziniereCTT']")).click();
    }

    @Then("Viene sottomesso il form e creato un nuovo magazziniere")
    public void viene_sottomesso_il_form_e_creato_un_nuovo_magazziniere() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        String originalWindow = driver1.getWindowHandle();
        assert driver1.getWindowHandles().size() == 1;
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();

        Thread.sleep(1000);

        for (String windowHandle : driver1.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver1.switchTo().window(windowHandle);
                break;
            }
        }
        driver1.close();
        driver1.switchTo().window(originalWindow);

        Thread.sleep(1000);
        String alertChk = "Dipendente SLNMJS46A28I123C aggiunto correttamente";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo operatore")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_operatore() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1500);

        driver1.findElement(By.id("codice")).sendKeys("SLNMPR46A28I321B");
        driver1.findElement(By.id("nome")).sendKeys("SeleniumOpe");
        driver1.findElement(By.id("cognome")).sendKeys("TesterOpe");
        driver1.findElement(By.id("datanasc")).sendKeys("12/10/1999");
        driver1.findElement(By.id("username")).sendKeys("seleniumOpe");
        driver1.findElement(By.id("rl")).click();
        driver1.findElement(By.xpath("//option[@value = 'OperatoreCTT']")).click();
    }

    @Then("Viene sottomesso il form e creato un novo operatore")
    public void viene_sottomesso_il_form_e_creato_un_novo_operatore() throws InterruptedException {
        String originalWindow = driver1.getWindowHandle();
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        Thread.sleep(1000);

        for (String windowHandle : driver1.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver1.switchTo().window(windowHandle);
                break;
            }
        }
        driver1.close();
        driver1.switchTo().window(originalWindow);

        Thread.sleep(1000);
        String alertChk = "Dipendente SLNMPR46A28I321B aggiunto correttamente";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @When("Viene compilato il form errato per l'aggiunta di un nuovo amministratore")
    public void viene_compilato_il_form_errato_per_l_aggiunta_di_un_nuovo_amministratore() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1500);
        driver1.findElement(By.id("codice")).sendKeys("SLNPQM46A28I321D");
        driver1.findElement(By.id("nome")).sendKeys("SeleniumAmm");
        driver1.findElement(By.id("cognome")).sendKeys("TesterAmm");
        driver1.findElement(By.id("datanasc")).sendKeys("12/10/2024");
        driver1.findElement(By.id("username")).sendKeys("seleniumAmm");
        driver1.findElement(By.id("rl")).click();
        driver1.findElement(By.xpath("//option[@value = 'AmministratoreCTT']")).click();
    }

    @Then("Viene sottomesso il form e non creato il nuovo amministratore")
    public void viene_sottomesso_il_form_e_non_creato_il_nuovo_amministratore() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "La data di nascita non puÃ² essere successiva a quella odierna";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }
}
