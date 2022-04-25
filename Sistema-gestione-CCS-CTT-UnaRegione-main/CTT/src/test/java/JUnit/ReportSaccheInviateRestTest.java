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

public class ReportSaccheInviateRestTest {

    static String token = null;
    Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget reportSaccheInviate = client.target("http://127.0.0.1:8081/rest/amministratore/reportLocaleSaccheInviate");

    /**Droppa il database*/
    @After
    public void dropDB() {
        MongoDataManager mm = MongoDataManager.getInstance();
        mm.dropDB();
    }

    /** Test per il metodo /rest/amministratore/reportLocaleSaccheInviate dell'amministratoreCTT, va a buon fine*/
    @Test
    public void testCorretto(){
		Client client = ClientBuilder.newClient();

		Form form1 = new Form();
		form1.param("username", "admin1");
		form1.param("password", "Adminadmin1");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

        Response responseReport = reportSaccheInviate.queryParam("dataInizio", "2000-07-10").queryParam("dataFine", "2022-07-10").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Status.OK.getStatusCode(), responseReport.getStatus());
    }
    
    /** Test per il metodo /rest/amministratore/reportLocaleSaccheInviate dell'amministratoreCTT,
     *  non va a buon fine siccome le date sono inserite in un formato errato*/
    @Test
    public void testFormatoErrato(){
		Client client = ClientBuilder.newClient();

		Form form1 = new Form();
		form1.param("username", "admin1");
		form1.param("password", "Adminadmin1");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

        Response responseReport = reportSaccheInviate.queryParam("dataInizio", "07-10-2000").queryParam("dataFine", "07-10-2022").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Status.BAD_REQUEST.getStatusCode(), responseReport.getStatus());
    }
}