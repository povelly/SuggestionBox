package com.sorbonne.safetyline.model;


import javax.persistence.Entity;


public class Admin extends User{
    public Admin(String idUser, String password) {
        super(idUser, password);
    }

    public Admin() {

    }


    public String toString()
    {
        String res = "";
        res += "idUser : "+super.getId_user()+" password :"+this.getPassword() + " is admin: ";
        return res;
    }
}
