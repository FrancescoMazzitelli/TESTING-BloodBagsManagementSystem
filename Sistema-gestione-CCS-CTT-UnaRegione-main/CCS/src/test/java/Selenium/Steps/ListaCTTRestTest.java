package Selenium.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import org.junit.Assert;
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

public class ListaCTTRestTest {

    public static WebDriver driver1, driver2;
    String urlIn = "http://127.0.0.1:8080/Login.html";
    String urlOut = "http://127.0.0.1:8080/ChangePasswordAfterLogin.html";
    MongoDataManager mm = MongoDataManager.getInstance();
    static String token = null;
    Client client = ClientBuilder.newClient();
    WebTarget login = client.target("http://127.0.0.1:8080/rest/autentificazione");
    WebTarget listaCTT = client.target("http://127.0.0.1:8080/rest/CCS/centers");
    Form form, form1;

    @Given("L'amministratore si autenitica sul portale")
    public void l_amministratore_si_autenitica_sul_portale(){
        System.setProperty("webdriver.edge.driver", "src/test/resources/Selenium_WebDrivers/msedgedriver.exe");
        driver1 = new EdgeDriver();

        driver1.get(urlIn);
        driver1.manage().window().maximize();
        driver1.findElement(By.id("user")).sendKeys("admin");
        driver1.findElement(By.id("pass")).sendKeys("Adminadmin1");
        driver1.findElement(By.id("btnLogin")).click();
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT per aumentare il numero in lista")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_per_aumentare_il_numero_in_lista() throws InterruptedException {
        Thread.sleep(1000);
        driver1.findElement(By.xpath("//button[contains(text(), 'Aggiunta nuovo CTT')]")).click();
        Thread.sleep(1000);

        driver1.findElement(By.id("provincia")).sendKeys("NA");
        driver1.findElement(By.id("citta")).sendKeys("Cucumber");
        driver1.findElement(By.id("indirizzo")).sendKeys("via dei test selenium 10");
        driver1.findElement(By.id("telefono")).sendKeys("9876543210");
        driver1.findElement(By.id("mail")).sendKeys("selenium@gmail.com");
        driver1.findElement(By.id("latitude")).sendKeys("45");
        driver1.findElement(By.id("longitude")).sendKeys("12");

        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        driver1.switchTo().alert().accept();
    }

    @Then("Viene sottomesso il form e restituita la dimensione di una lista di CTT")
    public void viene_sottomesso_il_form_e_restituita_la_dimensione_di_una_lista_di_CTT(){
        form = new Form();
        form.param("username", "admin");
        form.param("password", "Adminadmin1");

        Response responselogin = login.request().post(Entity.form(form));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();

        listaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(3, mm.getListaCTT().size());
    }

    @When("Viene compilato il form per l'aggiunta di un nuovo CTT sbagliato per auementare il numero in lista")
    public void viene_compilato_il_form_per_l_aggiunta_di_un_nuovo_CTT_sbagliato_per_auementare_il_numero_in_lista() throws InterruptedException {
        boolean flag = false;
        driver1.findElement(By.id("provincia")).sendKeys("BN");
        driver1.findElement(By.id("citta")).sendKeys("Selenium");
        driver1.findElement(By.id("indirizzo")).sendKeys("via dei test cucumber 12");
        driver1.findElement(By.id("telefono")).sendKeys("1234567");
        driver1.findElement(By.id("mail")).sendKeys("cucumber@gmail.com");
        driver1.findElement(By.id("latitude")).sendKeys("1");
        driver1.findElement(By.id("longitude")).sendKeys("100");

        driver1.findElement(By.xpath("//button[contains(text(), 'Conferma')]")).click();
        Thread.sleep(1000);
        String alert = driver1.switchTo().alert().getText();
        driver1.switchTo().alert().accept();
        String alertChk = "Formato del numero di telefono non valido";

        if(alert.equalsIgnoreCase(alertChk)) flag = true;
        Assertions.assertTrue(flag);
    }

    @Then("Viene sottomesso il form e non restituita la dimensione di una lista di CTT")
    public void viene_sottomesso_il_form_e_non_restituita_la_dimensione_di_una_lista_di_CTT(){
        listaCTT.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
        Assertions.assertEquals(3, mm.getListaCTT().size());
        driver1.close();
    }

}
