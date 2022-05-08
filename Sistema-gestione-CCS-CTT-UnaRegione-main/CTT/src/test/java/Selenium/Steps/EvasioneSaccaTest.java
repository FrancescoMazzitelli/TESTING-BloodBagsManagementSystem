package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Notifiche.NotificaEvasione;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Seriale;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EvasioneSaccaTest {

    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
    WebTarget evasioneSacca = client.target("http://127.0.0.1:8081/rest/magazziniere/evasione");
    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8081/Login.html";
    String urlOut = "http://127.0.0.1:8081/AmministratoreCTT.html";
    Seriale ser1 = new Seriale("CTT001-00000011");
    Seriale ser2 = new Seriale("CTT001-00000012");
    Seriale ser3 = new Seriale("CTT001-00000013");
    Seriale ser4 = new Seriale();
    Seriale ser5 = new Seriale();
    Seriale ser6 = new Seriale();
    NotificaEvasione not, not1;

    @Given("Viene compilato il form per l'autenticazione del magazziniere ed effettuato l'accesso")
    public void viene_compilato_il_form_per_l_autenticazione_del_magazziniere_ed_effettuato_l_accesso() throws MalformedURLException {
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();

        //WebDriverManager.edgedriver().setup();

        options.addArguments("test-type");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.setCapability("platform", Platform.LINUX);

        //URL remoteUrl = new URL("http://192.168.1.125:4444/wd/hub");
        URL remoteUrl = new URL("http://172.17.0.2:4444/wd/hub");

        driver1 = new RemoteWebDriver(remoteUrl, options);
        driver1.get(urlIn);
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        //driver1.findElement(By.id("btnLogin")).click();
        WebElement element = driver1.findElement(By.id("btnLogin"));
        JavascriptExecutor executor = (JavascriptExecutor)driver1;
        executor.executeScript("arguments[0].click();", element);

        Form form1 = new Form();
        form1.param("username", "admin");
        form1.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }

    @When("Viene create una notifica di evasione sacche")
    public void vieneCreateUnaNotificaDiEvasioneSacche() {
        List<Seriale> listaSeriali = new ArrayList<Seriale>();
        listaSeriali.add(ser1);
        listaSeriali.add(ser2);
        listaSeriali.add(ser3);
        System.err.println(ser3);
        String enteRichiedente= "Ospedale Rummo";
        String indirizzoEnte="Benevento, via pacevecchia 12";

        not= new NotificaEvasione(listaSeriali, enteRichiedente, indirizzoEnte, "messaggio");
    }

    @Then("La notifica veiene accettata e le sacche vengono evase")
    public void laNotificaVeieneAccettataELeSaccheVengonoEvase() {
        Response evasioneSaccaMagazz = evasioneSacca.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.json(not));
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), evasioneSaccaMagazz.getStatus());
    }

    @When("Viene createa un altra notifica di evasione sacche con dei seriali non presenti nel DB")
    public void vieneCreateaUnAltraNotificaDiEvasioneSaccheConDeiSerialiNonPresentiNelDB() {
        List<Seriale> listaSeriali = new ArrayList<Seriale>();
        listaSeriali.add(ser5);
        Seriale nonPresente = new Seriale("CTT001-00000123");
        listaSeriali.add(nonPresente);
        listaSeriali.add(ser6);
        String enteRichiedente= "Ospedale Rummo";
        String indirizzoEnte="Benevento, via pacevecchia 12";

        not1= new NotificaEvasione(listaSeriali, enteRichiedente, indirizzoEnte, "messaggio");
    }

    @Then("La notifica viene accettata e la sacche non vengono evase")
    public void laNotificaVieneAccettataELaSaccheNonVengonoEvase() {
        Response evasioneSaccaMagazz = evasioneSacca.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).post(Entity.json(not1));
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), evasioneSaccaMagazz.getStatus());
        driver1.close();
    }
}
