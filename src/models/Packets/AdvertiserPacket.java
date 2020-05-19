package models.Packets;

import models.TypeOfPacket;

public class AdvertiserPacket implements Packet{
    private String guid;
    private TypeOfPacket type;
    private String topicName;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public TypeOfPacket getType() {
        return type;
    }

    public void setType(TypeOfPacket type) {
        this.type = type;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
