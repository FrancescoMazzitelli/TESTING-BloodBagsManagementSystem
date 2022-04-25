package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class RemoveCTTRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget rimozioneCTT = client.target("http://127.0.0.1:8080/rest/CCS/rimozioneCTT");

    @Given("L'amministratore accede al portale")
    public void L_amministratore_accede_al_portale(){
        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }


    @Then("Vengono eliminati due CTT")
    public void vengonoEliminatiDueCTT() {
        Response responseRemCTT5 = rimozioneCTT.path("CTT005").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemCTT5.getStatus());
        Response responseRemCTT4 = rimozioneCTT.path("CTT001").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemCTT4.getStatus());
    }

    @Then("Non viene eliminato il CTT target")
    public void nonVieneEliminatoIlCTTTarget() {
        Response responseRemCTT = rimozioneCTT.path("CTT008").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseRemCTT.getStatus());
    }
}
