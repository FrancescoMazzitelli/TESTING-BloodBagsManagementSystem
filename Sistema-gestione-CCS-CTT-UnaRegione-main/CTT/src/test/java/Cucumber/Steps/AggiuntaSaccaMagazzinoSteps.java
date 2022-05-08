package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.GruppoSanguigno;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class AggiuntaSaccaMagazzinoSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget aggiuntaSaccaMagazz = client.target("http://127.0.0.1:8081/rest/magazziniere/aggiuntaSacca");
    Form form1, form2, form3, form4;

    @Given("Viene compilato il form per l'accesso al portale del magazziniere")
    public void viene_compilato_il_form_per_l_accesso_al_portale_del_magazziniere(){
        Form form = new Form();
        form.param("username", "username 006");
        form.param("password", "Password6");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene compilato il form per l'aggiunta di una nuova sacca")
    public void vieneCompilatoIlFormPerLAggiuntaDiUnaNuovaSacca() {
        form1 = new Form();
        form1.param("gruppo_sanguigno", GruppoSanguigno.Ap.toString());
        form1.param("data_scadenza", "2025-11-10");
        form1.param("data_produzione", "2022-03-12");
        form1.param("ente_donatore", "DonatoreProva1");

        form2 = new Form();
        form2.param("gruppo_sanguigno", GruppoSanguigno.Ap.toString());
        form2.param("data_scadenza", "2025-11-10");
        form2.param("data_produzione", "2022-04-20");
        form2.param("ente_donatore", "DonatoreProva1");

        form3 = new Form();
        form3.param("gruppo_sanguigno", GruppoSanguigno.Am.toString());
        form3.param("data_scadenza", "2025-11-10");
        form3.param("data_produzione", "2022-04-20");
        form3.param("ente_donatore", "DonatoreProva2");
    }

    @Then("Viene sottomesso il form e creata una nuova sacca")
    public void vieneSottomessoIlFormECreataUnaNuovaSacca() {
        Response responseaddSaccaMagazz = aggiuntaSaccaMagazz.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form1));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseaddSaccaMagazz.getStatus());

        Response responseaddSaccaMagazz1 = aggiuntaSaccaMagazz.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseaddSaccaMagazz1.getStatus());

        Response responseaddSaccaMagazz2 = aggiuntaSaccaMagazz.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form3));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseaddSaccaMagazz2.getStatus());
    }

    @When("Viene compilato il form errato per l'aggiunta di una nuova sacca")
    public void vieneCompilatoIlFormErratoPerLAggiuntaDiUnaNuovaSacca() {
        form4 = new Form();
        form4.param("gruppo_sanguigno", GruppoSanguigno.Ap.toString());
        form4.param("data_scadenza", "2016-11-10");
        form4.param("data_produzione", "2016-11-12");
        form4.param("ente_donatore", "DonatoreProva1");
    }

    @Then("Viene sottomesso il form e non creata una nuova sacca")
    public void vieneSottomessoIlFormENonCreataUnaNuovaSacca() {
        Response responseaddSaccaMagazz = aggiuntaSaccaMagazz.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseaddSaccaMagazz.getStatus());
    }
}
