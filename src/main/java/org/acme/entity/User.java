package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("_id")
    private String id;

    private String docType = "User";

    private String username;
    private String email;

    public User() {
    }

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

