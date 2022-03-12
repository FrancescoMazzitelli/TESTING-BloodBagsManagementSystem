package it.unisannio.ingegneriaDelSoftware.Util;

import java.util.LinkedList;

public class NetworkProperties {
    private final String CTT001 = "127.0.0.1";
    private final String CTT002 = "192.168.193.162";
    private final String CTT003 = "192.168.193.214";
    private final String PORT = "8081";
    private LinkedList<String> IP;

    public NetworkProperties() {
        this.IP = new LinkedList<String>();
        IP.add(getCTT001());
        IP.add(getCTT002());
        IP.add(getCTT003());
    }

    public LinkedList<String> getIP() {
        return IP;
    }

    public String getCTT001() {
        return CTT001;
    }

    public String getCTT002() {
        return CTT002;
    }

    public String getCTT003() {
        return CTT003;
    }

    public String getPORT() {
        return PORT;
    }
}
