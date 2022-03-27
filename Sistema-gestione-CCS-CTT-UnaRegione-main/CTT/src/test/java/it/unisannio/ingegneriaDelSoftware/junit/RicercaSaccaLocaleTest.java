package it.unisannio.ingegneriaDelSoftware.junit;

import it.unisannio.ingegneriaDelSoftware.DataManagers.MongoDataManager;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.Beans.User;
import it.unisannio.ingegneriaDelSoftware.DomainTypes.*;
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

public class RicercaSaccaLocaleTest {

	static MongoDataManager md = MongoDataManager.getInstance();
	static String token = null;
	Client client = ClientBuilder.newClient();
	WebTarget login = client.target("http://127.0.0.1:8081/rest/autentificazione");
	WebTarget ricercaLocale = client.target("http://127.0.0.1:8081/rest/operatore/ricerca");
	
  /**Popola il database di Sacche, aggiunge un AmministratoreCTT per eseguire la query ed effettua il login
     * @throws EntityAlreadyExistsException
     */
    @Before
    public void setUp() throws EntityAlreadyExistsException {

    	List<Sacca> listaSacche = new ArrayList<Sacca>();
    	List<DatiSacca> listaDatiSacche = new ArrayList<DatiSacca>();
    	
    	//Caricamento sul sistema di cinque Sacche di tipo A+, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana (2022)
    	//Una sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	GruppoSanguigno gs = GruppoSanguigno.Ap;
    	LocalDate localDataProduzione = LocalDate.of(2020,04,10);
    	LocalDate localDataScadenza = LocalDate.now().plusDays(2);
    	Sacca sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	listaSacche.add(sacca);
    	        
    	LocalDate localDataArrivo = LocalDate.of(2021,07,15);
    	String enteDonatore = "AVIS - Benevento";
    	DatiSacca datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.Ap;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.Ap;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.Ap;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.Ap;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2023,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Caricamento sul sistema di cinque Sacche di tipo A-, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana
    	//Una sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	 gs = GruppoSanguigno.Am;
    	 localDataProduzione = LocalDate.of(2020,04,10);
    	 localDataScadenza = LocalDate.of(2022,04,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2020,07,15);
    	 enteDonatore = "AVIS - Benevento";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.Am;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.Am;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.Am;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.Am;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2023,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Caricamento sul sistema di cinque Sacche di tipo B+, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana
    	//Una sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	 gs = GruppoSanguigno.Bp;
    	 localDataProduzione = LocalDate.of(2020,04,10);
    	 localDataScadenza = LocalDate.of(2022,04,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2020,07,15);
    	 enteDonatore = "AVIS - Benevento";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.Bp;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.Bp;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.Bp;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.Bp;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2023,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//Caricamento sul sistema di cinque Sacche di tipo B-, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana
    	//Una sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	 gs = GruppoSanguigno.Bm;
    	 localDataProduzione = LocalDate.of(2020,04,10);
    	 localDataScadenza = LocalDate.now().plusDays(2);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2020,07,15);
    	 enteDonatore = "AVIS - Benevento";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.Bm;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.Bm;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.Bm;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.Bm;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Caricamento sul sistema di cinque Sacche di tipo AB+, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana
    	//Una Sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	 gs = GruppoSanguigno.ABp;
    	 localDataProduzione = LocalDate.of(2020,04,10);
    	 localDataScadenza = LocalDate.of(2022,04,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2020,07,15);
    	 enteDonatore = "AVIS - Benevento";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.ABp;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.ABp;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.now().plusDays(2);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.ABp;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.ABp;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2023,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Caricamento sul sistema di cinque Sacche di tipo AB-, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana
    	//Una Sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	 gs = GruppoSanguigno.ABm;
    	 localDataProduzione = LocalDate.of(2020,04,10);
    	 localDataScadenza = LocalDate.of(2022,04,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2020,07,15);
    	 enteDonatore = "AVIS - Benevento";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.ABm;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.ABm;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.ABm;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.ABm;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Caricamento sul sistema di cinque Sacche di tipo ZERO+, 4 sacche sono arrivate nel magazzino tra il 15-07-2020 e il 02-05-2021 e hanno data di scadenza lontana
    	//Una Sacca è arrivata nel 2018 ed è già scaduta
    	//Tutte le Sacche sono non prenotate e quindi affidabili ad un ente esterno 

    	//Prima sacca 
    	 gs = GruppoSanguigno.ZEROp;
    	 localDataProduzione = LocalDate.of(2020,04,10);
    	 localDataScadenza = LocalDate.of(2022,04,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2020,07,15);
    	 enteDonatore = "AVIS - Benevento";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Seconda sacca 
    	 gs = GruppoSanguigno.ZEROp;
    	 localDataProduzione = LocalDate.of(2020,05,10);
    	 localDataScadenza = LocalDate.of(2022,05,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,03,9);
    	 enteDonatore = "AVIS - Avellino";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Terza sacca 
    	 gs = GruppoSanguigno.ZEROp;
    	 localDataProduzione = LocalDate.of(2020,06,10);
    	 localDataScadenza = LocalDate.of(2022,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Centrale";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 

    	//Quarta sacca
    	 gs = GruppoSanguigno.ZEROp;
    	 localDataProduzione = LocalDate.of(2020,07,12);
    	 localDataScadenza = LocalDate.of(2022,07,12);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Nord";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 


    	//La sacca scaduta
    	 gs = GruppoSanguigno.ZEROp;
    	 localDataProduzione = LocalDate.of(2018,06,10);
    	 localDataScadenza = LocalDate.of(2023,06,10);
    	 sacca = new Sacca(gs, localDataProduzione, localDataScadenza);
    	 listaSacche.add(sacca);
    	        
    	 localDataArrivo = LocalDate.of(2021,05,02);
    	 enteDonatore = "AVIS - Napoli_Sud";
    	 datisacca = new DatiSacca(sacca.getSeriale(), gs, localDataArrivo, null, enteDonatore, null,null);
    	 listaDatiSacche.add(datisacca); 
    	                                   
    	 MongoDataManager mm = MongoDataManager.getInstance();
    	    	
    	for(Sacca sac : listaSacche) {
    		mm.createSacca(sac);
        }
    	
    	for(DatiSacca datisac : listaDatiSacche) {
    		mm.createDatiSacca(datisac);
        }
    	
        Cdf cdf = Cdf.getCDF("XDDBHH45H57H684W");
        LocalDate ld = LocalDate.parse("2000-07-10");
        RuoloDipendente ruolo = RuoloDipendente.OperatoreCTT;
        String username = "username 002";
        String password = "Password2";
        Dipendente dip = new Dipendente(cdf, "Pino", "Perugini", ld, ruolo, username, password);

        mm.createDipendente(dip);

        Client client = ClientBuilder.newClient();
        Form form1 = new Form();
        form1.param("username", "username 002");
        form1.param("password", "Password2");

        Response responselogin = login.request().post(Entity.form(form1));
        User user = responselogin.readEntity(User.class);
        token = user.getToken();
    }
	
    /** Test per il metodo /rest/operatore/ricerca dell'operatoreCTT, va a buon fine*/
	@Test
	public void testCorretto(){
		Client client = ClientBuilder.newClient();
		Form form1 = new Form();
		form1.param("username", "username 004");
		form1.param("password", "Password4");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		WebTarget ricercaLocale1 = ricercaLocale.queryParam("gruppoSanguigno", "Bp")
									 .queryParam("numeroSacche", "2")
									 .queryParam("dataArrivoMassima", "2022-12-22")
									 .queryParam("enteRichiedente", "Ospedale Rummo")
									 .queryParam("indirizzoEnte", "Benevento, via pacevecchia 2")
									 .queryParam("priorità", "TRUE");
									 
		Response responseRicerca = ricercaLocale1.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
		Assertions.assertEquals(Status.OK.getStatusCode(), responseRicerca.getStatus());
	}
	
	/** Test per il metodo /rest/operatore/ricerca dell'operatoreCTT, non va a buon fine,
	 *  siccome non esiste nessuna Sacca ZERO- nel database delle Sacche*/
	@Test
	public void testNessunaSaccaTrovata(){
		Client client = ClientBuilder.newClient();
		Form form1 = new Form();
		form1.param("username", "username 004");
		form1.param("password", "Password4");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		WebTarget ricercaLocale1 = ricercaLocale.queryParam("gruppoSanguigno", "ZEROm")
									 .queryParam("numeroSacche", "1")
									 .queryParam("dataArrivoMassima", "2022-12-22")
									 .queryParam("enteRichiedente", "Ospedale Rummo")
									 .queryParam("indirizzoEnte", "Benevento, via pacevecchia 2")
									 .queryParam("priorità", "TRUE");
									 
		Response responseRicerca = ricercaLocale1.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
		Assertions.assertEquals(Status.NOT_FOUND.getStatusCode(), responseRicerca.getStatus());
	}

	/** Test per il metodo /rest/operatore/ricerca dell'operatoreCTT, non va a buon fine,
	 *  siccome non esistono abbastanza Sacche ZERO+ nel database delle Sacche per soddisfare completamente la richiesta in locale*/
	@Test
	public void testAlcuneSaccheTrovate(){
		Client client = ClientBuilder.newClient();
		Form form1 = new Form();
		form1.param("username", "username 004");
		form1.param("password", "Password4");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		WebTarget ricercaLocale1 = ricercaLocale.queryParam("gruppoSanguigno", "ZEROp")
									 .queryParam("numeroSacche", "10")
									 .queryParam("dataArrivoMassima", "2022-12-22")
									 .queryParam("enteRichiedente", "Ospedale Rummo")
									 .queryParam("indirizzoEnte", "Benevento, via pacevecchia 2")
									 .queryParam("priorità", "TRUE");
									 
		Response responseRicerca = ricercaLocale1.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
		Assertions.assertEquals(Status.PARTIAL_CONTENT.getStatusCode(), responseRicerca.getStatus());
	}

	/** Test per il metodo /rest/operatore/ricerca dell'operatoreCTT, non va a buon fine,
	 *  siccome il formato della data di arrivo massima è errato*/
	@Test
	public void testDatiFormatoErrato(){
		Client client = ClientBuilder.newClient();
		Form form1 = new Form();
		form1.param("username", "username 004");
		form1.param("password", "Password4");

		Response responselogin = login.request().post(Entity.form(form1));
		User user = responselogin.readEntity(User.class);
		token = user.getToken();

		WebTarget ricercaLocale1 = ricercaLocale.queryParam("gruppoSanguigno", "Ap")
									 .queryParam("numeroSacche", "5")
									 .queryParam("dataArrivoMassima", "04-12-2021")
									 .queryParam("enteRichiedente", "Ospedale Rummo")
									 .queryParam("indirizzoEnte", "Benevento, via pacevecchia 2")
									 .queryParam("priorità", "TRUE");
									 
		Response responseRicerca = ricercaLocale1.request().header(HttpHeaders.AUTHORIZATION, "Basic "+token).get();
		Assertions.assertEquals(Status.BAD_REQUEST.getStatusCode(), responseRicerca.getStatus());
	}
	
	@After public void dropDBSacche() {
		md.dropDB();
	}
	
}