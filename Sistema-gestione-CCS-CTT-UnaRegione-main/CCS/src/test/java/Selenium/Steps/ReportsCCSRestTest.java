package Selenium.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

public class ReportsCCSRestTest {

    public static WebDriver driver1;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget reportDipendentiCCS = client.target("http://127.0.0.1:8080/rest/CCS/reportDipendentiCCS");
    WebTarget reportGiacenzaMediaSacche = client.target("http://127.0.0.1:8080/rest/CCS/giacenzaMediaSaccheCCS");
    WebTarget reportSaccheInviate = client.target("http://127.0.0.1:8080/rest/CCS/reportSaccheInviateCCS");
    WebTarget reportSaccheRicevute = client.target("http://127.0.0.1:8080/rest/CCS/reportSaccheRicevuteCCS");
    WebTarget reportStatisticoSaccheRegionale = client.target("http://127.0.0.1:8080/rest/CCS/reportStatisticoSaccheCCS");

    @Given("Viene effettuata l'autenticazione dell'amministratore")
    public void viene_effettuata_l_autenticazione_dell_amministratore() throws InterruptedException, MalformedURLException {
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

        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        Thread.sleep(1000);
        //driver1.findElement(By.xpath("//button[contains(text(), 'Log In')]")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);
        Thread.sleep(2000);
        driver1.findElement(By.id("queryMenu")).click();

        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @Then("Viene richiesto il report dipendenti")
    public void vieneRichiestoIlReportDipendenti() {
        Response responseReport = reportDipendentiCCS.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene richiesto il report giagenza media")
    public void vieneRichiestoIlReportGiagenzaMedia() {
        Response responseReport = reportGiacenzaMediaSacche.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene richiesto il report sacche inviate")
    public void vieneRichiestoIlReportSaccheInviate() {
        Response responseReport = reportSaccheInviate.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene richiesto il report sacche ricevute")
    public void vieneRichiestoIlReportSaccheRicevute() {
        Response responseReport = reportSaccheRicevute.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene richiesto il report statistico sacche")
    public void vieneRichiestoIlReportStatisticoSacche(){
        Response responseReport = reportStatisticoSaccheRegionale.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }
}
