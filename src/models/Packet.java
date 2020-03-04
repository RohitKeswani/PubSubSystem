package models;

import java.io.Serializable;

public class Packet implements Serializable {
    private String guid;
    private String type;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
