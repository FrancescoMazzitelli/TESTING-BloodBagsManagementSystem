package it.unisannio.ingegneriaDelSoftware.junit;

import static org.junit.Assert.assertEquals;
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

import it.unisannio.ingegneriaDelSoftware.CcsRestApplication;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.*;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.Exceptions.EntityAlreadyExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;

public class RemoveCTTRestTest {
	
	static String token = null;
	Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
	WebTarget rimozioneCTT = client.target("http://127.0.0.1:8080/rest/CCS/rimozioneCTT");

	/**Droppa i database*/
	@After
	public  void dropDBCTT() {
		MongoDataManager mm = MongoDataManager.getInstance();
		mm.dropDB();
	}
	
	/**Test del metodo REST rest/CCS/rimozioneCTT, va a buon fine in quanto si tenta di eliminare un CTT inserito nel setUp
	 * @throws EntityAlreadyExistsException 
	 */
	@Test public void testRimozioneCTTCorretto() throws EntityAlreadyExistsException{
        /*List<CTT> listaCTT = new ArrayList<CTT>();

        int numero=6;
        String provincia = "NA";
        String citta = "Giugliano";
        String telefono = "0818955111";
        String indirizzo = "Via Montello 21";
        String email = "sangiuliano@aruba.com";
        double latitudine = 44.5;
        double longitudine = 20.6;
        CTT CTT001 = new CTT(CTTName.getCttName("CTT00"+numero), provincia, citta, telefono, indirizzo, email, latitudine, longitudine);
        listaCTT.add(CTT001);

        MongoDataManager mm = MongoDataManager.getInstance();

        for(CTT ctt : listaCTT) {
            mm.createCTT(ctt);
        }

         */

        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

		Response responseRemCTT5 = rimozioneCTT.path("CTT005").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		Assertions.assertEquals(Status.OK.getStatusCode(), responseRemCTT5.getStatus());
        Response responseRemCTT4 = rimozioneCTT.path("CTT004").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Status.OK.getStatusCode(), responseRemCTT4.getStatus());
	} 	
	
	/**Test del metodo REST rest/CCS/rimozioneCTT, non deve andare a buon fine in quanto si tenta di eliminare un CTT non presente nel database
	 * @throws EntityAlreadyExistsException 
	*/ 
	@Test public void testRimozioneCTTNonPresente() throws EntityAlreadyExistsException{
        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

		Response responseRemCTT = rimozioneCTT.path("CTT008").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
		Assertions.assertEquals(Status.NOT_FOUND.getStatusCode(), responseRemCTT.getStatus());
	} 	
}