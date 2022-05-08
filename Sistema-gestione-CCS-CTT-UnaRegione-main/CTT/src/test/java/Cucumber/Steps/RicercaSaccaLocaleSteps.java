package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class RicercaSaccaLocaleSteps {

    static MongoDataManager md = MongoDataManager.getInstance();
    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget ricercaLocale = client.target("http://127.0.0.1:8081/rest/operatore/ricerca");
    WebTarget ricercaLocale1, ricercaLocale2;

    @Given("L'amministratore si logga sul portale")
    public void l_amministratore_si_logga_sul_portale(){
        Form form1 = new Form();
        form1.param("username", "username 004");
        form1.param("password", "Password4");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene compilato il form per la ricerca")
    public void vieneCompilatoIlFormPerLaRicerca() {
        ricercaLocale1 = ricercaLocale.queryParam("gruppoSanguigno", "Ap")
                .queryParam("numeroSacche", "1")
                .queryParam("dataArrivoMassima", "2025-01-02")
                .queryParam("enteRichiedente", "Ospedale Rummo")
                .queryParam("indirizzoEnte", "Benevento, via pacevecchia 2");
    }

    @Then("Viene sottomesso il form e ricercata la sacca")
    public void vieneSottomessoIlFormERicercataLaSacca() {
        Response responseRicerca = ricercaLocale1.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRicerca.getStatus());
    }

    @When("Viene compilato il form errato per la ricerca")
    public void vieneCompilatoIlFormErratoPerLaRicerca() {
        ricercaLocale2 = ricercaLocale.queryParam("gruppoSanguigno", "Ap")
                .queryParam("numeroSacche", "5")
                .queryParam("dataArrivoMassima", "04-12-2021")
                .queryParam("enteRichiedente", "Ospedale Rummo")
                .queryParam("indirizzoEnte", "Benevento, via pacevecchia 2")
                .queryParam("priorità", "TRUE");
    }

    @Then("Viene sottomesso il form e non ricercata la sacca")
    public void vieneSottomessoIlFormENonRicercataLaSacca() {
        Response responseRicerca = ricercaLocale2.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseRicerca.getStatus());
    }
}
