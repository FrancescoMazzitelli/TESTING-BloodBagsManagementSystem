package Cucumber.Steps;

import io.cucumber.java.en.*;
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

public class ListaCTTRestSteps {

    MongoDataManager mm = MongoDataManager.getInstance();
    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget aggiuntaCTT = client.target("http://127.0.0.1:8080/rest/CCS/aggiuntaCTT");
    WebTarget listaCTT = client.target("http://127.0.0.1:8080/rest/CCS/centers");
    Form form, form1, form2;

    @Given("L'amministratore si autenitica sul portale")
    public void L_amministratore_si_autenitica_sul_portale(){
        form = new Form();
        form.param("username", "admin");
        form.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT per aumentare il numero in lista")
    public void vieneCompilatoIlFormPerLAggiuntaDiUnNuovoCTTPerAumentareIlNumeroInLista() {
        form1 = new Form();
        form1.param("provincia", "BN");
        form1.param("citta", "Campolattaro");
        form1.param("indirizzo", "Via del testing 12");
        form1.param("telefono", "0821432576");
        form1.param("email", "CTT005@gmail.com");
        form1.param("latitude", "65.00");
        form1.param("longitude", "41.00");
    }

    @Then("Viene sottomesso il form e restituita la dimensione di una lista di CTT")
    public void vieneSottomessoIlFormERestituitaLaDimensioneDiUnaListaDiCTT() {
        Response responseaddCTT = aggiuntaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form1));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseaddCTT.getStatus());
        listaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(3, mm.getListaCTT().size());
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato per auementare il numero in lista")
    public void vieneCompilatoIlFormPerLAggiuntaDiUnNuovoCTTSbagliatoPerAuementareIlNumeroInLista() {
        form2 = new Form();
        form2.param("provincia", "BN");
        form2.param("citta", "Campolattaro");
        form2.param("indirizzo", "Via del testing 12");
        form2.param("telefono", "0821432576a");			//è stato inserito un input non valido per il campo telefono
        form2.param("email", "CTT005@gmail.com");
        form2.param("latitude", "65.00");
        form2.param("longitude", "41.00");
    }

    @Then("Viene sottomesso il form e non restituita la dimensione di una lista di CTT")
    public void vieneSottomessoIlFormENonRestituitaLaDimensioneDiUnaListaDiCTT() {
        aggiuntaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
        listaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertNotEquals(2, mm.getListaCTT().size());
    }
}
