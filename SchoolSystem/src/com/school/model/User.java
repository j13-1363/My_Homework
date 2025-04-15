package com.school.model;

public class User {
    private final String id;
    private final String type;

    public User(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}