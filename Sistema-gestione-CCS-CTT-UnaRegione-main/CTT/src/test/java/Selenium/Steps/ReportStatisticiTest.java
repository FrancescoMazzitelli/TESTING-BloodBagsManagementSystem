package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
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

public class ReportStatisticiTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget ReportDipendentiCTT = client.target("http://127.0.0.1:8081/rest/amministratore/reportDipendentiCtt");
    WebTarget reportGiacenzaMediaSacche = client.target("http://127.0.0.1:8081/rest/amministratore/giacenzaMediaSacche");
    WebTarget reportSaccheInviate = client.target("http://127.0.0.1:8081/rest/amministratore/reportLocaleSaccheInviate");
    WebTarget reportSaccheRicevute = client.target("http://127.0.0.1:8081/rest/amministratore/reportLocaleSaccheRicevute");
    WebTarget reportStatisticoSaccheLocale = client.target("http://127.0.0.1:8081/rest/amministratore/reportStatisticoSacche");

    @Given("L'amministratore accede al portale dei report")
    public void l_amministratore_accede_sul_portale_dei_report() throws InterruptedException {
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 002");
        form1.param("password", "Password2");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();
        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("username 003");
        driver1.findElement(By.id("pass")).sendKeys("Password3");
        driver1.findElement(By.id("btnLogin")).click();
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Report statistici')]")).click();
    }

    @Then("Vengono filtrati i magazzinieri")
    public void vengono_filtrati_i_magazzinieri() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Query Dipendenti')]")).click();
        Thread.sleep(1000);
        driver1.findElement(By.id("labelMag")).click();
        Thread.sleep(1000);
        String tagName = driver1.findElement(By.id("table")).findElement(By.xpath("//*[@id='table']/tr[1]/td[1]")).getText(); //*[@id="table"]/tr[1]/td[1]
        boolean flag = false;
        if(tagName != null) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Vengono filtrati gli operatori")
    public void vengono_filtrati_gli_operatori() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.id("labelOpe")).click();
        Thread.sleep(1000);
        String tagName = driver1.findElement(By.id("table")).findElement(By.xpath("//*[@id='table']/tr[1]/td[1]")).getText(); //*[@id="table"]/tr[1]/td[1]
        boolean flag = false;
        if(tagName != null) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Vengono filtrati gli amministratori")
    public void vengono_filtrati_gli_amministratori() throws InterruptedException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        Thread.sleep(1000);
        driver1.findElement(By.id("labelAmm")).click();
        Thread.sleep(1000);
        String tagName = driver1.findElement(By.id("table")).findElement(By.xpath("//*[@id='table']/tr[1]/td[1]")).getText(); //*[@id="table"]/tr[1]/td[1]
        boolean flag = false;
        if(tagName != null) flag = true;
        Assertions.assertTrue(flag);
        driver1.close();
    }

    @Then("Viene restituita la giacenza media delle sacche")
    public void vieneRestituitaLaGiacenzaMediaDelleSacche() {
        Response responseReport = reportGiacenzaMediaSacche.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene restituito il report sacche inviate")
    public void vieneRestituitoIlReportSaccheInviate() {
        Response responseReport = reportSaccheInviate.queryParam("dataInizio", "2000-07-10").queryParam("dataFine", "2022-07-10").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Non viene restituito il report sacche inviate a causa di un formato errato")
    public void nonVieneRestituitoIlReportSaccheInviateACausaDiUnFormatoErrato() {
        Response responseReport = reportSaccheInviate.queryParam("dataInizio", "07-10-2000").queryParam("dataFine", "07-10-2022").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene restituito il report sacche ricevute")
    public void vieneRestituitoIlReportSaccheRicevute() {
        Response responseReport = reportSaccheRicevute.queryParam("dataInizio", "2000-07-10").queryParam("dataFine", "2022-07-10").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Non viene restituito il report sacche ricevute a causa di un formato errato")
    public void nonVieneRestituitoIlReportSaccheRicevuteACausaDiUnFormatoErrato() {
        Response responseReport = reportSaccheRicevute.queryParam("dataInizio", "07-10-2000").queryParam("dataFine", "07-10-2022").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseReport.getStatus());
    }

    @Then("Viene restituito il report statistico sacche")
    public void vienenRestituitoIlReportStatisticoSacche() {
        Response responseReport = reportStatisticoSaccheLocale.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

}
