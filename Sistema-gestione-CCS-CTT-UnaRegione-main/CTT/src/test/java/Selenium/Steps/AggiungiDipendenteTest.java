package Selenium.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.CttRestApplication;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;


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
    public void l_utente_effettua_l_accesso_sull_apposito_portale(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password3");
        driver1.findElement(By.id("btnLogin")).click();
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
