package it.unisannio.ingegneriaDelSoftware.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**Classe che carica i settings dai file XML presenti in /localsettings*/
public class Settings {

    public static final String ccsIp;
    public static final int retry ;
    public static final String ccsWebSocket;
    public static final String ccsIpPort;
    public static final String DB_NAME;
    public static final String COLLECTION_DIPENDENTI;
    public static final String COLLECTION_SACCHE;
    public static final String COLLECTION_DATISACCHE;
    public static final List<String> trustedIp;

    static {
        Properties loadNetworkProps = new Properties();
        Properties loadDatabaseProps = new Properties();
        NetworkProperties np = new NetworkProperties();
        DatabaseProperties dp = new DatabaseProperties();
        /*try {
            String localDir = System.getProperty("user.dir");
            loadNetworkProps.loadFromXML(new FileInputStream("Sistema-gestione-CCS-CTT-UnaRegione-main\\CTT\\src\\localsettings\\network_settings.xml"));
            loadDatabaseProps.loadFromXML(new FileInputStream("Sistema-gestione-CCS-CTT-UnaRegione-main\\CTT\\src\\localsettings\\database_settings.xml"));

        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        ccsIp = np.getCCSIP();
        ccsWebSocket = np.getCCSWebSocketIP();
        retry = np.getCCS_retry_connection();
        ccsIpPort = np.getCCS_PORT();

        LinkedHashMap<String, String> propIP = new LinkedHashMap<String, String>();
        propIP.put("AmministratoreIP",np.getAmministratoreIP());
        propIP.put("MagazziniereIP", np.getMagazziniereIP());
        propIP.put("OperatoreIP", np.getOperatoreIP());
        Set<String> ips = propIP.keySet();

        //carico ip sicuri
        trustedIp = new ArrayList<>();
        for (String keyS : ips) {
            if(keyS.equals("MagazziniereIP") || keyS.equals("OperatoreIP") || keyS.equals("AmministratoreIP") )
                trustedIp.add(propIP.get(keyS));
        }

        //carico impostazioni DB
        DB_NAME = dp.getDB_NAME();
        COLLECTION_DIPENDENTI = dp.getCOLLECTION_DIPENDENTI();
        COLLECTION_SACCHE = dp.getCOLLECTION_SACCHE();
        COLLECTION_DATISACCHE = dp.getCOLLECTION_DATISACCHE();
    }

}