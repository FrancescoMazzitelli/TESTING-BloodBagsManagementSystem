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
    WebTarget Login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget LogOut = client.target("http://127.0.0.1:8080/rest/autentificazione/logout");
    Form form1;

    @Given("L'admin si autentica tramite form")
    public void L_admin_si_autentica_tramite_form(){
        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");
        Response responselogin = Login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }


    @Then("Viene rimosso il token generato dal il login precedente")
    public void vieneRimossoIlTokenGeneratoDalIlLoginPrecedente() {
        Response responseLogout = LogOut.request().header(HttpHeaders.AUTHORIZATION, "errato "+token).delete();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseLogout.getStatus());
    }

    @Then("Non viene rimosso il token poichè è errato")
    public void nonVieneRimossoIlTokenPoichèÈErrato() {
        Response responseLogout = LogOut.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseLogout.getStatus());
    }
}
