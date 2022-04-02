package it.unisannio.ingegneriaDelSoftware.junit;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import it.unisannio.ingegneriaDelSoftware.CcsRestApplication;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;

public class LogOutRestTest {
	
	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget Login = client.target("http://127.0.0.1:8080/rest/autentificazione");
	WebTarget LogOut = client.target("http://127.0.0.1:8080/rest/autentificazione/logout");

	/**Droppa i database*/
	@After
	public void dropDB(){
		MongoDataManager mm = MongoDataManager.getInstance();
		mm.dropDB();
	}

	
	/**Test per verificare che il Logout non vada a buon fine in presenza di un Token errato
	 * @throws EntityNotFoundException
	 */
	  @Test
	 public void testRimozioneTokenNonPresente() throws EntityNotFoundException {
		  Form form1 = new Form();
		  form1.param("username", "admin");
		  form1.param("password", "Adminadmin1");
		  Response responselogin = Login.request().post(Entity.form(form1));
		  User user = responselogin.readEntity(User.class);
		  token = user.getToken();
		  Response responseLogout = LogOut.request().header(HttpHeaders.AUTHORIZATION, "errato "+token).delete();
		Assertions.assertEquals(Status.NOT_FOUND.getStatusCode(), responseLogout.getStatus());
		}
	  
	  
	  /**Test per verificare la corretta rimozione del token in seguito ad una richiesta di logout da parte dell'amministratoreCCS
		 * @throws EntityNotFoundException
		 */
	  @Test
	  public void testRimozioneToken() throws EntityNotFoundException{
		  Form form1 = new Form();
		  form1.param("username", "admin");
		  form1.param("password", "Adminadmin1");
		  Response responselogin = Login.request().post(Entity.form(form1));
		  User user = responselogin.readEntity(User.class);
		  token = user.getToken();
		  Response responseLogout = LogOut.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		  Assertions.assertEquals(Status.OK.getStatusCode(), responseLogout.getStatus());
	  }
}