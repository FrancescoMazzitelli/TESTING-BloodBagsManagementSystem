package stepDefinitions.Steps;

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
import javax.ws.rs.core.Response;

public class RecuperoPasswordRestCTTSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget RecuperoPassword = client.target("http://127.0.0.1:8081/rest/autentificazione/recuperoPassword");
    MongoDataManager mm = MongoDataManager.getInstance();
    String username;

    @Given("L'admin si autentica sul portale")
    public void L_admin_si_autentica_sul_portale(){
        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene inserita una nuova password")
    public void vieneInseritaUnaNuovaPassword() {
        username = "admin";
    }

    @Then("Viene sottomesso il form e ottenuta una nuova password")
    public void vieneSottomessoIlFormEOttenutaUnaNuovaPassword() {
        Response responseRecuperoPassword = RecuperoPassword.path("KTMFSW67T64I460X").request().put(Entity.text(username));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRecuperoPassword.getStatus());
    }

    @Then("Non viene sottomesso il form")
    public void nonVieneSottomessoIlForm() {
        Response responseRecuperoPassword = RecuperoPassword.path("FALSOW67T64I460X").request().put(Entity.text(username));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseRecuperoPassword.getStatus());
    }

    @Then("Non viene sottomesso il form per altro utente")
    public void nonVieneSottomessoIlFormPerAltroUtente() {
        Response responseRecuperoPassword = RecuperoPassword.path("KTMFSW67T64I460A").request().put(Entity.text(username));
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseRecuperoPassword.getStatus());
    }
}
