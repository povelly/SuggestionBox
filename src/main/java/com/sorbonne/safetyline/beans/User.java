package com.sorbonne.safetyline.beans;

public class User
{
    private String idUser;
    private String password;
    private boolean isAdmin;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User(String idUser, String password, boolean isAdmin)
    {
        this.idUser = idUser;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
