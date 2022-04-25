package it.unisannio.ingegneriaDelSoftware.Util;

import java.util.LinkedList;

public class NetworkProperties {
    private final String CTT001 = "127.0.0.1";
    private final String CTT002 = "192.168.193.162";
    private final String CTT003 = "192.168.193.214";
    private final String PORT = "8081";
    private LinkedList<String> IPs;

    public NetworkProperties() {
        this.IPs = new LinkedList<String>();
        IPs.add(getCTT001());
        IPs.add(getCTT002());
        IPs.add(getCTT003());
    }

    public LinkedList<String> getIPs() {
        return IPs;
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
