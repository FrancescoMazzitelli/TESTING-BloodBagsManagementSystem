package it.unisannio.ingegneriaDelSoftware.junit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Cdf;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Dipendente;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.RuoloDipendente;
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

public class ReportDipendentiCTTRestTest {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget ReportDipendentiCTT = client.target("http://127.0.0.1:8081/rest/amministratore/reportDipendentiCtt");

    /**Droppa il database*/
    @After
    public void dropDB() {
        MongoDataManager mm = MongoDataManager.getInstance();
        mm.dropDB();
    }

    
    /** Test per il metodo rest/amministratore/reportDipendentiCtt dell'amministratoreCTT, che filtra i magazzieriCTT, va a buon fine*/
    @Test
    public void testMagazzinieri(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 002");
        form1.param("password", "Password2");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

        Response responseReport = ReportDipendentiCTT.queryParam("ruolo", RuoloDipendente.MagazziniereCTT).request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Status.OK.getStatusCode(), responseReport.getStatus());
    }
    
    /** Test per il metodo rest/amministratore/reportDipendentiCtt dell'amministratoreCTT, che filtra gli OperatoriCTT, va a buon fine*/
    @Test
    public void testReportOperatori(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 002");
        form1.param("password", "Password2");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

        Response responseReport = ReportDipendentiCTT.queryParam("ruolo", RuoloDipendente.OperatoreCTT).request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Status.OK.getStatusCode(), responseReport.getStatus());
    }
    
    /** Test per il metodo rest/amministratore/reportDipendentiCtt dell'amministratoreCTT, che filtra gli AmministratoriCTT, va a buon fine*/
    @Test
    public void testReportAmministratore(){
        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 002");
        form1.param("password", "Password2");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

        Response responseReport = ReportDipendentiCTT.queryParam("ruolo", RuoloDipendente.AmministratoreCTT).request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(Status.OK.getStatusCode(), responseReport.getStatus());
    }
}