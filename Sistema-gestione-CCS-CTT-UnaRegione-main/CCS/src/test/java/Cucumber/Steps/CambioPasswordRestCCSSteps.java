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

public class CambioPasswordRestCCSSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget CambioPassword = client.target("http://127.0.0.1:8080/rest/autentificazione/cambiopassword");
    String password, passwordErrata;

    @Given("L'utente si autentica sull'apposito portale tramite form admin1")
    public void l_utente_si_autentica_sull_apposito_portale_tramite_form_admin1(){
        Form form = new Form();
        form.param("username", "admin");
        form.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }
    @When("Viene inserita la nuova password")
    public void viene_inserita_la_nuova_password() {
        // Write code here that turns the phrase above into concrete actions
        password = "Adminadmin1";
    }
    @Then("Viene sottomessa e cambiata la password")
    public void viene_sottomessa_e_cambiata_la_password() {
        // Write code here that turns the phrase above into concrete actions
        Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460X").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password));
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseCambioPassword.getStatus());
    }

    @When("Viene inserita la nuova password errata")
    public void viene_inserita_la_nuova_password_errata() {
        passwordErrata = "passworderrata";
    }
    @Then("Viene sottomessa e non cambiata la password a causa del suo formato")
    public void viene_sottomessa_e_non_cambiata_la_password_a_causa_del_suo_formato() {
        Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460X").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(passwordErrata));
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseCambioPassword.getStatus());
    }

    @Then("Viene sottomessa e non cambiata la password a causa del token errato")
    public void vieneSottomessaENonCambiataLaPasswordACausaDelTokenErrato() {
        password = "Adminadmin1";
        Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460X").request().header(HttpHeaders.AUTHORIZATION, "errato "+token).put(Entity.text(password));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseCambioPassword.getStatus());
    }

    @Then("Viene sottomessa e non cambiata la password perchè il CF inserito non è lo stesso dell'utente autenticato")
    public void viene_Sottomessa_E_Non_Cambiata_La_Password_Perchè_Il_CF_Inserito_Non_È_Lo_Stesso_Dell_Utente_Autenticato() {
        password = "Adminadmin1";
        Response responseCambioPassword = CambioPassword.path("MFDFSW67T89I460X").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password));
        Assertions.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), responseCambioPassword.getStatus());
    }
}
