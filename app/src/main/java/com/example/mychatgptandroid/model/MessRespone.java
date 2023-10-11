package com.example.mychatgptandroid.model;

import java.util.List;

public class MessRespone {
    String id;
    String object;
    List<Choices> choicesList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Choices> getChoicesList() {
        return choicesList;
    }

    public void setChoicesList(List<Choices> choicesList) {
        this.choicesList = choicesList;
    }
}
