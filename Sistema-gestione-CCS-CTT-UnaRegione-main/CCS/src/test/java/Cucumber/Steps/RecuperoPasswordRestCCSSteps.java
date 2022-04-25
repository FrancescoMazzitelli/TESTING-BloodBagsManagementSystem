package Cucumber.Steps;

import io.cucumber.java.en.*;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class RecuperoPasswordRestCCSSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget RecuperoPassword = client.target("http://127.0.0.1:8080/rest/autentificazione/recuperoPassword");
    MongoDataManager mm = MongoDataManager.getInstance();
    String username, username1, username2;

    @Given("Viene immesso l'username")
    public void viene_immesso_l_username(){
        username = "admin2";
    }

    @Then("Viene sottomesso l'username e recuperata la password")
    public void vieneSottomessoLUsernameERecuperataLaPassword() {
        Response responseRecuperoPassword = RecuperoPassword.path("KTMFSW67T64I460F").request().put(Entity.text(username));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRecuperoPassword.getStatus());
    }

    @Given("Viene immesso l'username di un utente presente il cui codice fiscale è errato")
    public void vieneImmessoLUsernameDiUnUtentePresenteIlCuiCodiceFiscaleÈErrato() {
        username1 = "admin";
    }

    @Then("Viene sottomessso l'username e non recuperata la password")
    public void vieneSottomesssoLUsernameENonRecuperataLaPassword() {
        Response responseRecuperoPassword = RecuperoPassword.path("FALSOW67T64I460X").request().put(Entity.text(username1));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseRecuperoPassword.getStatus());
    }

    @Given("Viene immesso l'username di un utente presente nel db ma viene associato al CF di un altro utente")
    public void vieneImmessoLUsernameDiUnUtentePresenteNelDbMaVieneAssociatoAlCFDiUnAltroUtente() {
        username2 = "admin4";
    }

    @Then("Viene sottomessso l'username e non recuperata la password perchè il CF non corrisponde")
    public void vieneSottomesssoLUsernameENonRecuperataLaPasswordPerchèIlCFNonCorrisponde() {
        Response responseRecuperoPassword = RecuperoPassword.path("KTMFSW67T64I460X").request().put(Entity.text(username2));
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseRecuperoPassword.getStatus());
    }
}
