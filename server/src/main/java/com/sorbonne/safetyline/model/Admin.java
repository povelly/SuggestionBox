package com.sorbonne.safetyline.model;

public class Admin extends User{
    public Admin(String idUser, String password) {
        super(idUser, password);
    }

    public String toString()
    {
        String res = "";
        res += "idUser : "+super.getId_user()+" password :"+this.getPassword() + " is admin: ";
        return res;
    }
}
