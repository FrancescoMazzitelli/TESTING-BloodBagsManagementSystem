package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class LoginSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget Login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    Form form, formE;

    @Given("Viene compilato il form per l'accesso di un dipendente corretto")
    public void viene_compilato_il_form_per_l_accesso_di_un_dipendente_corretto(){
        form = new Form();
        form.param("username", "admin");
        form.param("password", "Adminadmin1");
    }

    @Then("Viene sottomesso il form ed effettuato l'accesso")
    public void vieneSottomessoIlFormEdEffettuatoLAccesso() {
        Response responseLogin = Login.request().post(Entity.form(form));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseLogin.getStatus());
    }

    @Given("Viene compilato un form errato per l'accesso di un dipendente")
    public void vieneCompilatoUnFormErratoPerLAccessoDiUnDipendente() {
        formE = new Form();
        formE.param("username", "admiN");
        formE.param("password", "Admin");
    }

    @Then("Viene sottomesso il form e non effettuato l'accesso")
    public void vieneSottomessoIlFormENonEffettuatoLAccesso() {
        Response responseLogin = Login.request().post(Entity.form(formE));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseLogin.getStatus());
    }
}
