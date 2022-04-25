package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.unisannio.ingegneriaDelSoftware.CcsRestApplication;
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

public class AggiungiAmministratoreRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget aggiuntaAmministratore = client.target("http://127.0.0.1:8080/rest/CCS/aggiuntaAmministratore");
    Form form1, form2;

    @Given("BeforeAll")
    public void BeforeAll(){
        CcsRestApplication.main();
    }

    @Given("L'utente si autentica sull'apposito portale tramite form")
    public void l_utente_si_autentica_sull_apposito_portale_tramite_form() {

        client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("username", "admin");
        form.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo amministratore")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_amministratore() {

        form1 = new Form();
        form1.param("cdf", "BVNZDG48A06D684R");
        form1.param("nome", "Lucio");
        form1.param("cognome", "Merlino");
        form1.param("dataDiNascita", "1977-04-01");
        form1.param("ruolo", RuoloDipendente.AmministratoreCCS.toString());
        form1.param("username", "username 234");
        form1.param("password", "Password234");
    }

    @Then("Viene sottomesso il form e creato un nuovo amministratore")
    public void viene_sottomesso_il_form_e_creato_un_nuovo_amministratore() {
        Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form1));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseaddAmm.getStatus());
    }

    @When("Viene compilato il form sbagliato per l'aggiunta di un nuovo amministratore")
    public void viene_compilato_il_form_sbagliato_per_l_aggiunta_di_un_nuovo_amministratore() {

        form2 = new Form();
        form2.param("cdf", "LZZBHR41C46C446V");
        form2.param("nome", "Lucio");
        form2.param("cognome", "Spora");
        form2.param("dataDiNascita", "01-04-1977"); //formato errato
        form2.param("ruolo", RuoloDipendente.AmministratoreCCS.toString());
        form2.param("username", "username 234");
        form2.param("password", "Password234");

    }

    @Then("Viene sottomesso il form e NON viene creato un nuovo amministratore")
    public void viene_sottomesso_il_form_e_NON_viene_creato_un_nuovo_amministratore() {
        Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseaddAmm.getStatus());
    }
}
