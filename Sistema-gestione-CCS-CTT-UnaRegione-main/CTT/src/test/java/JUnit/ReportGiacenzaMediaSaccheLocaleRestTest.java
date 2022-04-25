package JUnit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.*;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportGiacenzaMediaSaccheLocaleRestTest {
    static String token = null;
    Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget reportGiacenzaMediaSacche = client.target("http://127.0.0.1:8081/rest/amministratore/giacenzaMediaSacche");

    /**Droppa il database*/
    @After
    public void dropDB() {
        MongoDataManager mm = MongoDataManager.getInstance();
        mm.dropDB();
    }


    /** Test per il metodo /rest/amministratore/giacenzaMediaSacche dell'amministratoreCTT, va a buon fine*/
    @Test
    public void testCorretto(){
		Client client = ClientBuilder.newClient();

		Form form1 = new Form();
		form1.param("username", "username 003");
		form1.param("password", "Password3");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		Response responseReport = reportGiacenzaMediaSacche.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Status.OK.getStatusCode(), responseReport.getStatus());
    }
}