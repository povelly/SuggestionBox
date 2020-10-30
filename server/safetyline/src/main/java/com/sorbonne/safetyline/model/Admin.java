package com.sorbonne.safetyline.model;


import javax.persistence.Entity;


public class Admin extends User{

    public Admin() {

    }


    public String toString()
    {
        String res = "";
        res += "idUser : "+super.getUser_id()+" password :"+this.getPassword()
                + " is admin: ";
        return res;
    }
}
