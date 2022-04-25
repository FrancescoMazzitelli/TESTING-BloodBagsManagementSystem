package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class ReportsCCSRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget reportDipendentiCCS = client.target("http://127.0.0.1:8080/rest/CCS/reportDipendentiCCS");
    WebTarget reportGiacenzaMediaSacche = client.target("http://127.0.0.1:8080/rest/CCS/giacenzaMediaSaccheCCS");
    WebTarget reportSaccheInviate = client.target("http://127.0.0.1:8080/rest/CCS/reportSaccheInviateCCS");
    WebTarget reportSaccheRicevute = client.target("http://127.0.0.1:8080/rest/CCS/reportSaccheRicevuteCCS");
    WebTarget reportStatisticoSaccheRegionale = client.target("http://127.0.0.1:8080/rest/CCS/reportStatisticoSaccheCCS");

    @Given("Viene effettuata l'autenticazione dell'amministratore")
    public void viene_effettuata_l_autenticazione_dell_amministratore(){
        Form form1 = new Form();
        form1.param("username", "username 003");
        form1.param("password", "Password3");

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
