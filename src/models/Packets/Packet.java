package models.Packets;

import models.TypeOfPacket;

public interface Packet {
    String guid = null;
    TypeOfPacket type = null;

    String getGuid();

    void setGuid(String guid);

    TypeOfPacket getType();

    void setType(TypeOfPacket type);
}
