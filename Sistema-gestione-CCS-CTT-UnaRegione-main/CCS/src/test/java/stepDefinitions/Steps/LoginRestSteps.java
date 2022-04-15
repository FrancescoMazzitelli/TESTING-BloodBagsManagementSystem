package stepDefinitions.Steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class LoginRestSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget Login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    Form form1, form2;

    @Given("Viene compilato il form per il login")
    public void viene_compilato_il_form_per_il_login(){
        form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");
    }

    @Then("Viene sottomesso il form e garantito l'accesso")
    public void vieneSottomessoIlFormEGarantitoLAccesso() {
        Response responseLogin = Login.request().post(Entity.form(form1));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), responseLogin.getStatus());
    }

    @Given("Viene compilato il form per il login di un admin non presente")
    public void vieneCompilatoIlFormPerIlLoginDiUnAdminNonPresente() {
        form2 = new Form();
        form2.param("username", "admiN");
        form2.param("password", "Admin");
    }

    @Then("Viene sottomesso il form e non garantito l'accesso")
    public void vieneSottomessoIlFormENonGarantitoLAccesso() {
        Response responseLogin = Login.request().post(Entity.form(form2));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseLogin.getStatus());
    }
}
