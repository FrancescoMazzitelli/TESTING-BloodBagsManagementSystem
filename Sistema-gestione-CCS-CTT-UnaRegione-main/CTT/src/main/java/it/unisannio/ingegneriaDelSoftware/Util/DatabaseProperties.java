package it.unisannio.ingegneriaDelSoftware.Util;

public class DatabaseProperties {
    private final String DB_NAME = "CTT001";
    private final String COLLECTION_DIPENDENTI = "DIPENDENTI";
    private final String COLLECTION_SACCHE = "SACCHE";
    private final String COLLECTION_DATISACCHE = "DATISACCHE";

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

    public String getCOLLECTION_DATISACCHE() {
        return COLLECTION_DATISACCHE;
    }
}
