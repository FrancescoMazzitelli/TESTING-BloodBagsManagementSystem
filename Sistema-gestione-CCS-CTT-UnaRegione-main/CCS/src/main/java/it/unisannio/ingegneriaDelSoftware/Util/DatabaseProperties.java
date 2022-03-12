package it.unisannio.ingegneriaDelSoftware.Util;

import java.util.LinkedList;

public class DatabaseProperties {
    private final String DB_NAME = "CCS";
    private final String COLLECTION_DIPENDENTI = "DIPENDENTI";
    private final String COLLECTION_SACCHE = "SACCHE_IN_SCADENZA";
    private final String COLLECTION_CTT = "CTT";

    public DatabaseProperties() {
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public String getCOLLECTION_DIPENDENTI() {
        return COLLECTION_DIPENDENTI;
    }

    public String getCOLLECTION_SACCHE() {
        return COLLECTION_SACCHE;
    }

    public String getCOLLECTION_CTT() {
        return COLLECTION_CTT;
    }
}
