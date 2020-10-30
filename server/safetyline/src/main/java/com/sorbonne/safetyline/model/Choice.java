package com.sorbonne.safetyline.model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table( name="Choice", schema = "safetyline")
public class Choice
{
    @Id
    private int id_choice;
    private static int index = 0;
    private int voters_cpt;

    //@ManyToMany
    //private List<User> userList;



    public Choice(int id_choice, String content) {
        this.id_choice = index++;
        this.voters_cpt = 0;
        this.content = content;
    }

    public Choice() {

    }
    //public List<User> getUserList() {
    //return userList;
    //}

    //public void setUserList(List<User> userList) {
    //    this.userList = userList;
   // }
    @Id
    public int getId_choice() {
        return id_choice;
    }

    public void setId_choice(int id_choice) {
        this.id_choice = id_choice;
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        Choice.index = index;
    }

    public int getVoters_cpt() {
        return voters_cpt;
    }

    public void setVoters_cpt(int voters_cpt) {
        this.voters_cpt = voters_cpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

}
