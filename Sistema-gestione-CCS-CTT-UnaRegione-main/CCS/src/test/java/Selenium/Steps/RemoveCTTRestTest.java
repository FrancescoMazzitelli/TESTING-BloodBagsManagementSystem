package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public class RemoveCTTRestTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/AmministratoreCCS.html";

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget rimozioneCTT = client.target("http://127.0.0.1:8080/rest/CCS/rimozioneCTT");


    @Given("L'amministratore accede al portale")
    public void l_amministratore_accede_al_portale() throws InterruptedException {
        driver1 = new EdgeDriver();
        driver1.get(urlIn);

        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Log In')]")).click();

        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @Then("Vengono eliminati due CTT")
    public void vengono_eliminati_due_CTT() throws InterruptedException {

        Thread.sleep(2000);
        boolean flag = false;
        driver1.findElement(By.xpath("//button[contains(text(), 'Rimozione CTT')]")).click();
        Thread.sleep(1000);
        /* Parte di codice della rimozione amministratore, l'HTML della rimozione CTT è rotto
        driver1.findElement(By.id("codice")).findElement(By.xpath("//option[@value = 'SLNMJS46A28I123C']")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "Corretta rimozione del Dipendente: SLNMJS46A28I123C";
        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
         */

        Response responseRemCTT5 = rimozioneCTT.path("CTT001").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemCTT5.getStatus());
        Response responseRemCTT4 = rimozioneCTT.path("CTT005").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), responseRemCTT4.getStatus());
    }

    @Then("Non viene eliminato il CTT target")
    public void nonVieneEliminatoIlCTTTarget() {
        Response responseRemCTT = rimozioneCTT.path("CTT008").request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).delete();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseRemCTT.getStatus());
        driver1.close();
    }
}
