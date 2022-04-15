package stepDefinitions.Steps;

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

public class RemoveDipendenteCTTSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget rimozioneDip = client.target("http://127.0.0.1:8081/rest/amministratore/rimozioneDipendente");

    @Given("L'amministratore si autentica sul portale")
    public void L_amministratore_si_autentica_sul_portale(){
        Form form1 = new Form();
        form1.param("username", "username 003");
        form1.param("password", "Password3");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }


    @Then("Vengono eliminati due utenti")
    public void vengonoEliminatiDueUtenti() {
        Response responseRemDip0 = rimozioneDip.path("LZZBHR41C46C446V").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemDip0.getStatus());

        Response responseRemDip1 = rimozioneDip.path("SRNGJZ50B54C143L").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemDip1.getStatus());
    }

    @Then("Non vengono eliminati gli utenti")
    public void nonVengonoEliminatiGliUtenti() {
        Response responseRemDip = rimozioneDip.path("FALSOG48A06D684R").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseRemDip.getStatus());
    }

    @Then("Non viene eliminato l'utente poichè non è possibile eliminare se stessi")
    public void nonVieneEliminatoLUtentePoichèNonÈPossibileEliminareSeStessi() {
        Response responseRemDip = rimozioneDip.path("CZGMJS46A28I333C").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseRemDip.getStatus());
    }
}
