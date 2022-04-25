package Cucumber.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Notifiche.NotificaEvasione;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Seriale;
import org.junit.jupiter.api.Assertions;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class EvasioneSaccaSteps {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget evasioneSacca = client.target("http://127.0.0.1:8081/rest/magazziniere/evasione");
    Seriale ser1 = new Seriale();
    Seriale ser2 = new Seriale();
    Seriale ser3 = new Seriale();
    Seriale ser4 = new Seriale();
    Seriale ser5 = new Seriale();
    Seriale ser6 = new Seriale();
    NotificaEvasione not, not1;

    @Given("Viene compilato il form per l'autenticazione del magazziniere ed effettuato l'accesso")
    public void viene_compilato_il_form_per_l_autenticazione_del_magazziniere_ed_effettuato_l_accesso(){
        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene create una notifica di evasione sacche")
    public void vieneCreateUnaNotificaDiEvasioneSacche() {
        List<Seriale> listaSeriali = new ArrayList<Seriale>();
        listaSeriali.add(ser1);
        listaSeriali.add(ser2);
        listaSeriali.add(ser3);
        System.err.println(ser3);
        String enteRichiedente= "Ospedale Rummo";
        String indirizzoEnte="Benevento, via pacevecchia 12";

        not= new NotificaEvasione(listaSeriali, enteRichiedente, indirizzoEnte, "messaggio");
    }

    @Then("La notifica veiene accettata e le sacche vengono evase")
    public void laNotificaVeieneAccettataELeSaccheVengonoEvase() {
        Response evasioneSaccaMagazz = evasioneSacca.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.json(not));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), evasioneSaccaMagazz.getStatus());
    }

    @When("Viene createa un altra notifica di evasione sacche con dei seriali non presenti nel DB")
    public void vieneCreateaUnAltraNotificaDiEvasioneSaccheConDeiSerialiNonPresentiNelDB() {
        List<Seriale> listaSeriali = new ArrayList<Seriale>();
        listaSeriali.add(ser5);
        Seriale nonPresente = new Seriale();
        listaSeriali.add(nonPresente);
        listaSeriali.add(ser6);
        String enteRichiedente= "Ospedale Rummo";
        String indirizzoEnte="Benevento, via pacevecchia 12";

        not1= new NotificaEvasione(listaSeriali, enteRichiedente, indirizzoEnte, "messaggio");
    }

    @Then("La notifica viene accettata e la sacche non vengono evase")
    public void laNotificaVieneAccettataELaSaccheNonVengonoEvase() {
        Response evasioneSaccaMagazz = evasioneSacca.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.json(not1));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), evasioneSaccaMagazz.getStatus());
    }
}
