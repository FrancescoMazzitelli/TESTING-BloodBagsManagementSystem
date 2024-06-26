package Cucumber.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class ReportStatisticiSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget ReportDipendentiCTT = client.target("http://127.0.0.1:8081/rest/amministratore/reportDipendentiCtt");
    WebTarget reportGiacenzaMediaSacche = client.target("http://127.0.0.1:8081/rest/amministratore/giacenzaMediaSacche");
    WebTarget reportSaccheInviate = client.target("http://127.0.0.1:8081/rest/amministratore/reportLocaleSaccheInviate");
    WebTarget reportSaccheRicevute = client.target("http://127.0.0.1:8081/rest/amministratore/reportLocaleSaccheRicevute");
    WebTarget reportStatisticoSaccheLocale = client.target("http://127.0.0.1:8081/rest/amministratore/reportStatisticoSacche");

    @Given("L'amministratore accede al portale dei report")
    public void l_amministratore_accede_al_portale_dei_report(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 002");
        form1.param("password", "Password2");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @Then("Vengono filtrati i magazzinieri")
    public void vengonoFiltratiIMagazzinieri() {
        Response responseReport = ReportDipendentiCTT.queryParam("ruolo", RuoloDipendente.MagazziniereCTT).request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Vengono filtrati gli operatori")
    public void vengonoFiltratiGliOperatori() {
        Response responseReport = ReportDipendentiCTT.queryParam("ruolo", RuoloDipendente.OperatoreCTT).request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
    }

    @Then("Vengono filtrati gli amministratori")
    public void vengonoFiltratiGliAmministratori() {
        Response responseReport = ReportDipendentiCTT.queryParam("ruolo", RuoloDipendente.AmministratoreCTT).request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseReport.getStatus());
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
