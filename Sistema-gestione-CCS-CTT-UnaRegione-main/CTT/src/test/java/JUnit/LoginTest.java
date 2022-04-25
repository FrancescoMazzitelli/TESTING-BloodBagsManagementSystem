package JUnit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoginTest {
	
	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget Login = client.target("http://127.0.0.1:8081/rest/autentificazione");

	/**Droppa i database*/
	@After
	public void dropDB(){
		MongoDataManager mm = MongoDataManager.getInstance();
		mm.dropDB();
	}

	/**Test per verificare l'assenza di un DipendenteCTT all'interno del database tramite username e password passate nel form
	 * @throws EntityNotFoundException
	 */
	  @Test
	 public void testDipendenteNonPresente() throws EntityNotFoundException {
		Form form = new Form();
		form.param("username", "admiN");
		form.param("password", "Admin");
		Response responseLogin = Login.request().post(Entity.form(form));
		Assertions.assertEquals(Status.NOT_FOUND.getStatusCode(), responseLogin.getStatus());
		}
	  
	  /**Test per verificare la presenza di un DipendenteCTT creato nel setUp e aggiunto nel database dei Dipendenti
		 * @throws EntityNotFoundException
		 */
	  @Test
	  public void testDipendentePresente() throws EntityNotFoundException{
		  Form form = new Form();
		  form.param("username", "admin");
		  form.param("password", "Adminadmin1");
		  Response responseLogin = Login.request().post(Entity.form(form));
		  Assertions.assertEquals(Status.CREATED.getStatusCode(), responseLogin.getStatus());
	  }
}