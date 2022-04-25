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

public class CambioPasswordRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget CambioPassword = client.target("http://127.0.0.1:8081/rest/autentificazione/cambiopassword");
    MongoDataManager mm = MongoDataManager.getInstance();
    String password, passwordErrata, password1;

    @Given("Un amministratore si autentica sul portale")
    public void un_amministratore_si_autentica_sul_portale(){
        Form form = new Form();
        form.param("username", "admin1");
        form.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("viene inserita una nuova password")
    public void vieneInseritaUnaNuovaPassword() {
        password = "Adminadmin1";
    }

    @Then("Viene sottomessa la nuova password")
    public void vieneSottomessaLaNuovaPassword() {
        password = "Adminadmin1";
        Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460A").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseCambioPassword.getStatus());
    }

    @Then("Non viene sottomessa la nuova password a causa di un token errato")
    public void nonVieneSottomessaLaNuovaPasswordACausaDiUnTokenErrato() {
        password = "Adminadmin1";
        Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460A").request().header(HttpHeaders.AUTHORIZATION, "errato "+token).put(Entity.text(password));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseCambioPassword.getStatus());
    }

    @When("viene inserita una password errata")
    public void vieneInseritaUnaPasswordErrata() {
        passwordErrata = "passworderrata";
    }

    @Then("viene sottomessa la nuova password ma non cambiata a causa del formato")
    public void vieneSottomessaLaNuovaPasswordMaNonCambiataACausaDelFormato() {
        passwordErrata = "passworderrata";
        Response responseCambioPassword = CambioPassword.path("MFDFSW67T89I460A").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text("passworderrata"));
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseCambioPassword.getStatus());
    }

    @Then("Non viene sottomessa la nuova password poichè è specificato un codice fiscale diverso da quello dell'utente autenticato")
    public void nonVieneSottomessaLaNuovaPasswordPoichèÈSpecificatoUnCodiceFiscaleDiversoDaQuelloDellUtenteAutenticato() {
        password1 = "Password1";
        Response responseCambioPassword = CambioPassword.path("MFDFSW67T89I460A").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password1));
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseCambioPassword.getStatus());
    }
}
