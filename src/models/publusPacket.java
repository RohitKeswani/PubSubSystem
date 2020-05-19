package models;

import java.io.Serializable;
import java.util.List;

public class publusPacket implements Serializable {
    private String guid;
    private TypeOfPacket type;
    private String topicName;
    private List<String> topicList;
    private String content;

    public List<String> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

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
}
