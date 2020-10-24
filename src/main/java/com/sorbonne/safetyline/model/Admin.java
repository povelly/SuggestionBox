package com.sorbonne.safetyline.model;

public class Admin extends User{
    public Admin(String idUser, String password, Boolean adm) {
        super(idUser, password, adm);
    }

    public String toString()
    {
        String res = "";
        res += "idUser : "+this.getIdUser()+" password :"+this.getPassword() + " is admin: ";
        return res;
    }
}
