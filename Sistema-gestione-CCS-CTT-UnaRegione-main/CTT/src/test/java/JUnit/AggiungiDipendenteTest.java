package JUnit;

import it.unisannio.ingegneriaDelSoftware.CttRestApplication;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import it.unisannio.ingegneriaDelSoftware.Util.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;

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

public class AggiungiDipendenteTest {
	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
	WebTarget aggiuntaAmministratore = client.target("http://127.0.0.1:8081/rest/amministratore/aggiuntaDipendente");

	/**Metto in esecuzione l'applicazione*/
	/*
	@BeforeAll
	public static void run() throws InterruptedException {
		CttRestApplication.main();
	}

	 */

		/** Test per il metodo rest/amministratore/aggiuntaDipendente dell'amministratoreCTT, che va a buon fine
		 */
		@Test	
		public void testAggiuntaMagazziniere() throws EntityAlreadyExistsException {

			Client client = ClientBuilder.newClient();
			Form form1 = new Form();
			form1.param("username", "username 003");
			form1.param("password", "Password3");

			Response responselogin = login.request().post(Entity.form(form1));
			User user = responselogin.readEntity(User.class);
			token = user.getToken();

			Form form2 = new Form();
			form2.param("cdf", "SRNGJZ50B54C143L");
			form2.param("nome", "Ario");
			form2.param("cognome", "lucarelli");
			form2.param("dataDiNascita", "1988-07-04");
			form2.param("ruolo", RuoloDipendente.MagazziniereCTT.toString());
			form2.param("username", "username 123");
			//form2.param("password", "Password123");
			Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
			Assertions.assertEquals(Status.CREATED.getStatusCode(), responseaddAmm.getStatus());
		}
		
		
		/** Test per il metodo rest/amministratore/aggiuntaDipendente dell'amministratoreCTT, che va a buon fine
		 */
		@Test	
		public void testAggiuntaOperatore(){
			Client client = ClientBuilder.newClient();
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
			form2.param("dataDiNascita", "1977-04-01");
			form2.param("ruolo", RuoloDipendente.OperatoreCTT.toString());
			form2.param("username", "username 234");
			//form2.param("password", "Password234");
			Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
			Assertions.assertEquals(Status.CREATED.getStatusCode(), responseaddAmm.getStatus());
		}
		
		
		/** Test per il metodo rest/amministratore/aggiuntaDipendente dell'amministratoreCTT,
		 *  non funzionante a causa del formato errato della data di nascita
		 */
		@Test	
		public void testFormatoDatiErrato(){
			Client client = ClientBuilder.newClient();
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
			form2.param("ruolo", RuoloDipendente.AmministratoreCTT.toString());
			form2.param("username", "username 234");
			//form2.param("password", "Password234");
			Response responseaddAmm = aggiuntaAmministratore.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
			Assertions.assertEquals(Status.BAD_REQUEST.getStatusCode(), responseaddAmm.getStatus());
		}
		

		@After public void dropDBi() {
			MongoDataManager mm = MongoDataManager.getInstance();
			mm.dropDB();
		}
	}