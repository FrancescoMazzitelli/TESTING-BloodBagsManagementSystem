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

public class RemoveAmministratoreCCSRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget rimozioneAmm = client.target("http://127.0.0.1:8080/rest/CCS/rimozioneAmministratore");


    @Given("L'amministratore3 si autentica sul portale")
    public void L_amministratore3_si_autentica_sul_portale(){
        Form form1 = new Form();
        form1.param("username", "username 003");
        form1.param("password", "Password3");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }


    @Then("Viene rimosso il dipendente selezionato")
    public void vieneRimossoIlDipendenteSelezionato() {
        Response responseRemAmm = rimozioneAmm.path("BVNZDG48A06D684R").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemAmm.getStatus());
    }

    @Then("Non viene rimosso il dipendente selezionato poichè non presente nel db")
    public void nonVieneRimossoIlDipendenteSelezionatoPoichèNonPresenteNelDb() {
        Response responseRemAmm = rimozioneAmm.path("NONPRE53N07D684R").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseRemAmm.getStatus());
    }

    @Then("Non viene rimosso il dipendente selezionato perchè non è possibile rimuovere se stessi")
    public void nonVieneRimossoIlDipendenteSelezionatoPerchèNonÈPossibileRimuovereSeStessi() {
        Response responseRemAmm = rimozioneAmm.path("CZGMJS46A28I333C").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseRemAmm.getStatus());
    }
}
