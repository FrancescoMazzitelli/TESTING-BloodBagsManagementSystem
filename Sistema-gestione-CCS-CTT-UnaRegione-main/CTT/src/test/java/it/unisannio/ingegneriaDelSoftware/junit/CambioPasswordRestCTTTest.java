package it.unisannio.ingegneriaDelSoftware.junit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.time.LocalDate;

public class CambioPasswordRestCTTTest {
	
	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
	WebTarget CambioPassword = client.target("http://127.0.0.1:8081/rest/autentificazione/cambiopassword");
	MongoDataManager mm = MongoDataManager.getInstance();

	/**Droppa i database*/
	@After
	public void dropDB(){
		MongoDataManager mm = MongoDataManager.getInstance();
		mm.dropDB();
	}
	  
	  
	  /**Test per verificare la corretta modifica della password inserendone una nuova
		 * @throws AssertionError, EntityNotFoundException,WebApplicationException
		 */
	  @Test
	  public void testCambioPassword() throws AssertionError, EntityNotFoundException, WebApplicationException, EntityAlreadyExistsException {
		  Client client = ClientBuilder.newClient();
		  Form form1 = new Form();
		  form1.param("username", "admin1");
		  form1.param("password", "Adminadmin1");

		  Response responselogin = login.request().post(Entity.form(form1));
		  User user = responselogin.readEntity(User.class);
		  token = user.getToken();
		  String password1 = "Adminadmin1";
		  Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460A").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password1));
		  Assertions.assertEquals(Status.OK.getStatusCode(), responseCambioPassword.getStatus());
	  }
	
	
	/**Test per verificare che il Cambio password non vada a buon fine se tentato con un token errato
	 * @throws AssertionError, EntityNotFoundException,WebApplicationException
	 */
	  @Test
	 public void testCambioPasswordTokenErrato()  throws AssertionError, EntityNotFoundException,WebApplicationException{
		  Client client = ClientBuilder.newClient();
		  Form form1 = new Form();
		  form1.param("username", "admin1");
		  form1.param("password", "Adminadmin1");

		  Response responselogin = login.request().post(Entity.form(form1));
		  User user = responselogin.readEntity(User.class);
		  token = user.getToken();

		  String password1 = "Adminadmin1";
		Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460A").request().header(HttpHeaders.AUTHORIZATION, "errato "+token).put(Entity.text(password1));
		Assertions.assertEquals(Status.NOT_FOUND.getStatusCode(), responseCambioPassword.getStatus());
		}
	
	
	/**Test per verificare che il Cambio password non vada a buon fine se non rispetto il formato della password
	 * @throws AssertionError, EntityNotFoundException,WebApplicationException
	 */
	  @Test
	 public void testCambioPasswordNonRiuscito()  throws AssertionError, EntityNotFoundException,WebApplicationException {
		  Client client = ClientBuilder.newClient();
		  Form form1 = new Form();
		  form1.param("username", "admin");
		  form1.param("password", "Adminadmin1");

		  Response responselogin = login.request().post(Entity.form(form1));
		  User user = responselogin.readEntity(User.class);
		  token = user.getToken();

		  String password1 = "passworderrata";
		Response responseCambioPassword = CambioPassword.path("KTMFSW67T64I460A").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password1));
		Assertions.assertEquals(Status.FORBIDDEN.getStatusCode(), responseCambioPassword.getStatus());
		}
	
	  
	/**Test per verificare che il Cambio password non vada a buon fine se tentato su un altro utente
	 * @throws AssertionError, EntityNotFoundException,WebApplicationException
	 */
	  @Test
	 public void testCambioPasswordAltroUtente()  throws AssertionError, EntityNotFoundException,WebApplicationException{
		  Client client = ClientBuilder.newClient();
		  Form form1 = new Form();
		  form1.param("username", "admin");
		  form1.param("password", "Adminadmin1");

		  Response responselogin = login.request().post(Entity.form(form1));
		  User user = responselogin.readEntity(User.class);
		  token = user.getToken();

		  String password = "Password1";
		Response responseCambioPassword = CambioPassword.path("MFDFSW67T89I460A").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).put(Entity.text(password));
		Assertions.assertEquals(Status.FORBIDDEN.getStatusCode(), responseCambioPassword.getStatus());
		}

}