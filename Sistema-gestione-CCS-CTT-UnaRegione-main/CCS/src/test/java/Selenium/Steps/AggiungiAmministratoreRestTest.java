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

public class AggiungiAmministratoreRestTest {

    public static WebDriver driver1;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";
    String urlAgg = "http://127.0.0.1:8080/AggiuntaNuovoAmministratoreCCSForm.html";

    public AggiungiAmministratoreRestTest() throws MalformedURLException {
    }

    /*
        @Given("BeforeAll")
        public void beforeAll(){
            CcsRestApplication.main();
        }
    */
    @Given("L'utente si autentica sull'apposito portale tramite form")
    public void l_utente_si_autentica_sull_apposito_portale_tramite_form() throws MalformedURLException, InterruptedException {
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
        Thread.sleep(2000);
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo amministratore")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_amministratore() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(2000);
        driver1.findElement(By.id("AggiungiAmministratore")).click();
        //driver1.findElement(By.xpath("//button[@onclick = 'location.href='location.href='AggiuntaNuovoAmministratoreCCSForm.html']"));

        //Compilo il form
        driver1.findElement(By.id("codice")).sendKeys("SLNMJS46A28I123C");
        driver1.findElement(By.id("nome")).sendKeys("Selenium");
        driver1.findElement(By.id("cognome")).sendKeys("Tester");
        driver1.findElement(By.id("datanasc")).sendKeys("12/10/1999");
        driver1.findElement(By.id("username")).sendKeys("selenium");
    }

    @Then("Viene sottomesso il form e creato un nuovo amministratore")
    public void viene_sottomesso_il_form_e_creato_un_nuovo_amministratore() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1.findElement(By.xpath("//button[contains(text(),'Conferma')]")).click();

        boolean flag = false;

        String url = driver1.getCurrentUrl();
        WebElement p1 = driver1.findElement(By.id("p1"));
        WebElement p2 = driver1.findElement(By.id("p2"));
        WebElement p3 = driver1.findElement(By.id("p3"));
        WebElement p4 = driver1.findElement(By.id("p4"));
        WebElement p5 = driver1.findElement(By.id("p5"));

        Thread.sleep(2000);
        try{
            if(url.equalsIgnoreCase(urlAgg)){
                flag = true;
            }

            if(p1.findElement(By.className("errorPrint")).getText().equalsIgnoreCase("SI PREGA DI COMPILARE IL CAMPO")){
                flag = false;
            }

            if(p2.findElement(By.className("errorPrint")).getText().equalsIgnoreCase("SI PREGA DI COMPILARE IL CAMPO")){
                flag = false;
            }

            if(p3.findElement(By.className("errorPrint")).getText().equalsIgnoreCase("SI PREGA DI COMPILARE IL CAMPO")){
                flag = false;
            }

            if(p4.findElement(By.className("errorPrint")).getText().equalsIgnoreCase("SI PREGA DI COMPILARE IL CAMPO")){
                flag = false;
            }

            if(p5.findElement(By.className("errorPrint")).getText().equalsIgnoreCase("SI PREGA DI COMPILARE IL CAMPO")){
                flag = false;
            }
        }
        catch (Exception e) {System.err.println(e);}

        Assertions.assertTrue(flag);
    }

    @Given("Viene compilato il form sbagliato per l'aggiunta di un nuovo amministratore")
    public void viene_compilato_il_form_sbagliato_per_l_aggiunta_di_un_nuovo_amministratore() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);

        Thread.sleep(1000);
        driver1.findElement(By.id("AggiungiAmministratore")).click();
        //driver1.findElement(By.xpath("//button[@onclick = 'location.href='location.href='AggiuntaNuovoAmministratoreCCSForm.html']"));

        //Compilo il form
        driver1.findElement(By.id("codice")).sendKeys("SLNMJS46A28I1"); //Codice fiscale errato
        driver1.findElement(By.id("nome")).sendKeys("Selenium");
        driver1.findElement(By.id("cognome")).sendKeys("Tester");
        driver1.findElement(By.id("datanasc")).sendKeys("12/10/1999");
        driver1.findElement(By.id("username")).sendKeys("selenium");
    }

    @Then("Viene sottomesso il form e NON viene creato un nuovo amministratore")
    public void viene_sottomesso_il_form_e_NON_viene_creato_un_nuovo_amministratore() throws InterruptedException {
        driver1.findElement(By.xpath("//button[contains(text(),'Conferma')]")).click();

        boolean flag = true;

        Thread.sleep(1000);
        Alert alert = driver1.switchTo().alert();
        String alertMex = driver1.switchTo().alert().getText();
        
        alert.accept();

        String alertChk = "Il codice fiscale non Ã¨ nel formato corretto";

        if(alertMex.equalsIgnoreCase(alertChk)) flag = false;

        Assertions.assertFalse(flag);

        driver1.close();
    }
}
