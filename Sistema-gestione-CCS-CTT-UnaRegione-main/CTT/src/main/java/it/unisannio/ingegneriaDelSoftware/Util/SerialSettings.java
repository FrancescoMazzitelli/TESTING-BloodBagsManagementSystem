package it.unisannio.ingegneriaDelSoftware.Util;

public class SerialSettings {
    private static Integer last_assigned = 1;

    public SerialSettings(Integer last_assigned) {
        this.last_assigned = last_assigned;
    }

    public Integer getLast_assigned() {
        return last_assigned;
    }

    public void setLast_assigned(Integer last_assigned) {
        this.last_assigned = last_assigned;
    }
}
