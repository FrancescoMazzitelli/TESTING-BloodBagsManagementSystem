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

public class LogoutRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget LogOut = client.target("http://127.0.0.1:8081/rest/autentificazione/logout");

    @Given("L'utente si autentica sul portale")
    public void L_utente_si_autentica_sul_portale(){
        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }


    @Then("L'utente si disconnette")
    public void lUtenteSiDisconnette() {
        Response responseLogout = LogOut.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseLogout.getStatus());
    }

    @Then("Il logout non va a buon fine")
    public void ilLogoutNonVaABuonFine() {
        Response responseLogout = LogOut.request().header(HttpHeaders.AUTHORIZATION, "errato "+token).delete();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseLogout.getStatus());
    }
}
