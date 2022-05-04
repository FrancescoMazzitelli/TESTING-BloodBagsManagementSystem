package JUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.cucumber.java.*;
import it.unisannio.ingegneriaDelSoftware.CcsRestApplication;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import org.junit.jupiter.api.*;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;

public class AggiungiAmministratoreRestTest {

	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
	WebTarget aggiuntaAmministratore = client.target("http://127.0.0.1:8080/rest/CCS/aggiuntaAmministratore");
/*
	@BeforeAll
	public static void run(){
		CcsRestApplication.main();
	}
*/

	/**Droppa il database*/
	@After
	public  void dropDB() {
		MongoDataManager mm = MongoDataManager.getInstance();
		mm.dropDB();
	}

		/** Test per il metodo rest/CCS/aggiuntaAmministratore dell'amministratoreCCS, va a buon fine
		 * @throws EntityAlreadyExistsException 
		 */
		@Test	
		public void testCorretto() throws EntityAlreadyExistsException{
			Client client = ClientBuilder.newClient();
			WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
			Form form1 = new Form();
			form1.param("username", "username 003");
			form1.param("password", "Password3");

			Response responselogin = login.request().post(Entity.form(form1));
			User user = responselogin.readEntity(User.class);
			token = user.getToken();

			Form form2 = new Form();
			form2.param("cdf", "BVNZDG48A06D684R");
			form2.param("nome", "Lucio");
			form2.param("cognome", "Merlino");
			form2.param("dataDiNascita", "1977-04-01");
			form2.param("ruolo", RuoloDipendente.AmministratoreCCS.toString());
			form2.param("username", "username 234");
			form2.param("password", "Password234");
			Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
			Assertions.assertEquals(Status.CREATED.getStatusCode(), responseaddAmm.getStatus());
		}

		/** Test per il metodo rest/CCS/aggiuntaAmministratore dell'amministratoreCCS, non va a buon fine,
		 * siccome i dati dell'AmministratoreCCS da aggiungere sono in un formato errato
		 * @throws EntityAlreadyExistsException 
		 */
		@Test	
		public void testFormatoErrato() throws EntityAlreadyExistsException{
			Client client = ClientBuilder.newClient();
			WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
			Form form1 = new Form();
			form1.param("username", "username 003");
			form1.param("password", "Password3");

			Response responselogin = login.request().post(Entity.form(form1));
			User user = responselogin.readEntity(User.class);
			token = user.getToken();

			Form form2 = new Form();
			form2.param("cdf", "LZZBHR41C46C446V");
			form2.param("nome", "Lucio");
			form2.param("cognome", "Spora");
			form2.param("dataDiNascita", "01-04-1977"); //formato errato
			form2.param("ruolo", RuoloDipendente.AmministratoreCCS.toString());
			form2.param("username", "username 234");
			form2.param("password", "Password234");
			Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
			Assertions.assertEquals(Status.BAD_REQUEST.getStatusCode(), responseaddAmm.getStatus());
		}
	}