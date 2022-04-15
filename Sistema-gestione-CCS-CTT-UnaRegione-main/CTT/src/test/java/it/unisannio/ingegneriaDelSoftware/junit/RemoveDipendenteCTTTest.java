package it.unisannio.ingegneriaDelSoftware.junit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import it.unisannio.ingegneriaDelSoftware.Util.Constants;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class RemoveDipendenteCTTTest {
	
	static String token = null;
	Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
	WebTarget rimozioneDip = client.target("http://127.0.0.1:8081/rest/amministratore/rimozioneDipendente");

	/**Test del metodo REST rest/amministratore/rimozioneDipendente
	 * Questo test deve andare a buon fine in quanto si tenta di eliminare un Dipendente inserito nel @Before
	 */
	@Test public void testRimozioneDipendenteCTTCorretto(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 003");
        form1.param("password", "Password3");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

		Response responseRemDip0 = rimozioneDip.path("LZZBHR41C46C446V").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		Assertions.assertEquals(Status.OK.getStatusCode(), responseRemDip0.getStatus());

		Response responseRemDip1 = rimozioneDip.path("SRNGJZ50B54C143L").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		Assertions.assertEquals(Status.OK.getStatusCode(), responseRemDip1.getStatus());
	} 	
	
	
	/**Test del metodo REST rest/amministratore/rimozioneDipendente
	 * Questo test non deve andare a buon fine in quanto si tenta di eliminare un Dipendente non presente nel database
	*/ 
	@Test public void testRimozioneDipendenteCTTNonPresente(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 003");
        form1.param("password", "Password3");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

		Response responseRemDip = rimozioneDip.path("FALSOG48A06D684R").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		Assertions.assertEquals(Status.NOT_FOUND.getStatusCode(), responseRemDip.getStatus());
	} 
	
	
	
	/**Test del metodo REST rest/amministratore/rimozioneDipendente
	 * Questo test non deve andare a buon fine in quanto l'AmministratoreCTT tenta di eliminare se stesso dal database dei Dipendenti
	*/ 
	@Test public void testRimozioneDipendenteCTTSeStesso(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 003");
        form1.param("password", "Password3");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

		Response responseRemDip = rimozioneDip.path("CZGMJS46A28I333C").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		Assertions.assertEquals(Status.FORBIDDEN.getStatusCode(), responseRemDip.getStatus());
	} 
	
	
	/**Classe per l'eliminazione del database
	 */
	@After public void dropDBDip() {
		MongoDataManager mm = MongoDataManager.getInstance();
		mm.dropDB();
	}
	
}