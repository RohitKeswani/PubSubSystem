package models.Packets;

import models.TypeOfPacket;

import java.io.Serializable;
import java.util.List;

public class ServerPacket implements Packet, Serializable {
    private String guid;
    private TypeOfPacket type;
    private String topicName;
    private String content;
    private List<String> topicList;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    @Override
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public TypeOfPacket getType() {
        return type;
    }

    @Override
    public void setType(TypeOfPacket type) {
        this.type = type;
    }
}
