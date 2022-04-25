package it.unisannio.ingegneriaDelSoftware.Util;

public class NetworkProperties {
    private static final String CCSIP = "127.0.0.1";
    private static final String CCSWebSocketIP = "ws://127.0.0.1:8080/ws/saccheInScadenza";
    private static final int CCS_retry_connection = 30;
    private static final String MagazziniereIP = "192.168.193.57";
    private static final String OperatoreIP = "192.168.193.28";
    private static final String AmministratoreIP = "192.168.193.38";
    private static final String CCS_PORT = "8080";

    public NetworkProperties() {
    }

    public String getCCSIP() {
        return CCSIP;
    }

    public String getCCSWebSocketIP() {
        return CCSWebSocketIP;
    }

    public int getCCS_retry_connection() {
        return CCS_retry_connection;
    }

    public String getMagazziniereIP() {
        return MagazziniereIP;
    }

    public String getOperatoreIP() {
        return OperatoreIP;
    }

    public String getAmministratoreIP() {
        return AmministratoreIP;
    }

    public String getCCS_PORT() {
        return CCS_PORT;
    }
}
