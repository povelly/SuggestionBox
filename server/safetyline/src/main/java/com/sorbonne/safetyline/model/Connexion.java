package com.sorbonne.safetyline.model;

public class Connexion
{
    private String username;
    private String password;

    public Connexion() {}

    public Connexion(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
