package Selenium.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.CcsRestApplication;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class ReportsCCSRestTest {

    public static WebDriver driver1, driver2;
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
    public void viene_effettuata_l_autenticazione_dell_amministratore() throws InterruptedException {
        driver1 = new EdgeDriver();
        driver1.get(urlIn);

        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Log In')]")).click();
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
        driver1.close();
    }
}
