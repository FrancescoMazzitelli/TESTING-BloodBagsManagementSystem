package Cucumber.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class AggiungiCTTRestTestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    User user;
    WebTarget aggiuntaCTT = client.target("http://127.0.0.1:8080/rest/CCS/aggiuntaCTT");
    Form form1, form2, form3, form4;

    @Given("L'amministratore accede al portale tramite form")
    public void lAmministratoreAccedeAlPortaleTramiteForm() {
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("username", "admin");
        form.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT(){
        form1 = new Form();
        form1.param("provincia", "BN");
        form1.param("citta", "Campolattaro");
        form1.param("indirizzo", "Via del testing 13");
        form1.param("telefono", "0821432576");
        form1.param("email", "CTT005@gmail.com");
        form1.param("latitude", "65");
        form1.param("longitude", "41");
    }

    @Then("Viene sottomesso il form e creato un nuovo CTT")
    public void viene_sottomesso_il_form_e_creato_un_nuovo_CTT(){
        Response responseaddCTT = aggiuntaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form1));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseaddCTT.getStatus());
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, numero e nome")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_sbagliato_numero_e_nome(){
        form2 = new Form();
        form2.param("provincia", "BN");
        form2.param("citta", "Campolattaro");
        form2.param("indirizzo", "Via del testing 12");
        form2.param("telefono", "1234568791");
        form2.param("email", "CTT005gmail.com");
        form2.param("latitude", "65.00");
        form2.param("longitude", "41.00");
    }

    @Then("Viene sottomesso il form e non viene creato un nuovo CTT a causa del numero e nome CTT")
    public void viene_sottomesso_il_form_e_non_viene_creato_un_nuovo_CTT_a_causa_del_numero_e_nome_CTT(){
        Response responseaddCTT = aggiuntaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseaddCTT.getStatus());
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, telefono")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_ctt_sbagliato_telefono() {
        form3 = new Form();
        form3.param("provincia", "BN");
        form3.param("citta", "Campolattaro");
        form3.param("indirizzo", "Via del testing 12");
        form3.param("telefono", "08214325o6a");			//è stato inserito un input non valido per il campo telefono
        form3.param("email", "CTT005@gmail.com");
        form3.param("latitude", "65");
        form3.param("longitude", "41");

        //throw new io.cucumber.java.PendingException();
    }

    @Then("Viene sottomesso il form e non viene creato un nuovo CTT a causa del telefono")
    public void viene_sottomesso_il_form_e_non_viene_creato_un_nuovo_ctt_a_causa_del_telefono() {
        // Write code here that turns the phrase above into concrete actions
        Response responseaddCTT = aggiuntaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form3));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseaddCTT.getStatus());
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato, latitudine")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_sbagliato_latitudine(){
        form4 = new Form();
        form4.param("provincia", "BN");
        form4.param("citta", "Campolattaro");
        form4.param("indirizzo", "Via del testing 12");
        form4.param("telefono", "0821432576");
        form4.param("email", "CTT005@gmail.com");
        form4.param("latitude", "190");			//è stato inserito un input non valido per la latitudine
        form4.param("longitude", "41");
    }

    @Then("Viene sottomesso il form e non viene creato un nuovo CTT a causa della latitudine")
    public void viene_sottomesso_il_form_e_non_viene_creato_un_nuovo_CTT_a_causa_della_latitudine(){
        Response responseaddCTT = aggiuntaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form4));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseaddCTT.getStatus());
    }


}
