package it.unisannio.ingegneriaDelSoftware.junit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.GruppoSanguigno;
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

public class AggiuntaSaccaMagazzinoTest {
	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
	WebTarget aggiuntaSaccaMagazz = client.target("http://127.0.0.1:8081/rest/magazziniere/aggiuntaSacca");
	
	@Before public void setUp() throws EntityAlreadyExistsException {
		
		MongoDataManager md = MongoDataManager.getInstance();
		 
        Dipendente d = new Dipendente(Cdf.getCDF("RLISNR72C54F356H"), "Mario", "Magazz", LocalDate.parse("1950-07-10", DateTimeFormatter.ofPattern(Constants.DATEFORMAT)), RuoloDipendente.MagazziniereCTT, "admin", "Adminadmin1");
        md.createDipendente(d);
    	
        Client client = ClientBuilder.newClient();
		//WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
		Form form1 = new Form();
		form1.param("username", "admin");
		form1.param("password", "Adminadmin1");
		
		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();
	}
	
	@Test public void testAggiuntaSaccaCorretto() {
		Client client = ClientBuilder.newClient();
		//WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
		Form form1 = new Form();
		form1.param("username", "username 006");
		form1.param("password", "Password6");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		Form form2 = new Form();
		form2.param("gruppo_sanguigno", GruppoSanguigno.Ap.toString());
		form2.param("data_scadenza", "2023-11-10");
		form2.param("data_produzione", "2020-11-12");
		form2.param("ente_donatore", "DonatoreProva1");

		Response responseaddSaccaMagazz = aggiuntaSaccaMagazz.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
		Assertions.assertEquals(Status.CREATED.getStatusCode(), responseaddSaccaMagazz.getStatus());
	}
	
	@Test public void testAggiuntaSaccaScaduta() {
		Client client = ClientBuilder.newClient();
		//WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
		Form form1 = new Form();
		form1.param("username", "username 006");
		form1.param("password", "Password6");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		Form form2 = new Form();
		form2.param("gruppo_sanguigno", GruppoSanguigno.Ap.toString());
		form2.param("data_scadenza", "2016-11-10");
		form2.param("data_produzione", "2016-11-12");
		form2.param("ente_donatore", "DonatoreProva1");

		Response responseaddSaccaMagazz = aggiuntaSaccaMagazz.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.form(form2));
		Assertions.assertEquals(Status.BAD_REQUEST.getStatusCode(), responseaddSaccaMagazz.getStatus());
	}
	
	@After public void dropDBSacche() {
		MongoDataManager md = MongoDataManager.getInstance();
		md.dropDB();
	}
}