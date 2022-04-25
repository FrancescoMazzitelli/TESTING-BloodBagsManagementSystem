package Cucumber.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.CttRestApplication;
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

public class AggiungiDipendenteSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget aggiuntaAmministratore = client.target("http://127.0.0.1:8081/rest/amministratore/aggiuntaDipendente");
    Form form1, form2, form3, form4;

    @Given("BeforeAll")
    public void BeforeAll() throws InterruptedException {
        CttRestApplication.main();
    }

    @Given("L'utente effettua l'accesso sull'apposito portale")
    public void lUtenteEffettuaLAccessoSullAppositoPortale() {
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("username", "username 003");
        form.param("password", "Password3");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene compilato il form per l'aggiunta di un magazziniere")
    public void vieneCompilatoIlFormPerLAggiuntaDiUnMagazziniere() {
        form1 = new Form();
        form1.param("cdf", "SRNGJZ50B54C143L");
        form1.param("nome", "Ario");
        form1.param("cognome", "lucarelli");
        form1.param("dataDiNascita", "1988-07-04");
        form1.param("ruolo", RuoloDipendente.MagazziniereCTT.toString());
        form1.param("username", "username 123");
        form1.param("password", "Password123");
    }

    @Then("Viene sottomesso il form e creato un nuovo magazziniere")
    public void vieneSottomessoIlFormECreatoUnNuovoMagazziniere() {
        Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form1));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseaddAmm.getStatus());
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo operatore")
    public void vieneCompilatoIlFormPerLAggiuntaDiUnNuovoOperatore() {
        form2 = new Form();
        form2.param("cdf", "LZZBHR41C46C446V");
        form2.param("nome", "Lucio");
        form2.param("cognome", "Spora");
        form2.param("dataDiNascita", "1977-04-01");
        form2.param("ruolo", RuoloDipendente.OperatoreCTT.toString());
        form2.param("username", "username 234");
        form2.param("password", "Password234");
    }

    @Then("Viene sottomesso il form e creato un novo operatore")
    public void vieneSottomessoIlFormECreatoUnNovoOperatore() {
        Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseaddAmm.getStatus());
    }

    @When("Viene compilato il form errato per l'aggiunta di un nuovo amministratore")
    public void vieneCompilatoIlFormErratoPerLAggiuntaDiUnNuovoAmministratore() {
        form3 = new Form();
        form3.param("cdf", "LZZBHR41C46C446V");
        form3.param("nome", "Lucio");
        form3.param("cognome", "Spora");
        form3.param("dataDiNascita", "01-04-1977"); //formato errato
        form3.param("ruolo", RuoloDipendente.AmministratoreCTT.toString());
        form3.param("username", "username 234");
        form3.param("password", "Password234");
    }

    @Then("Viene sottomesso il form e non creato il nuovo amministratore")
    public void vieneSottomessoIlFormENonCreatoIlNuovoAmministratore() {
        Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseaddAmm.getStatus());
    }
}
