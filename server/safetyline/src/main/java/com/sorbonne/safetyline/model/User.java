package com.sorbonne.safetyline.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name="user", schema = "safetyline")
//@NamedQuery(name= "User.getAll", query = "select user from User user")
public class User
{
    @Id
    @NotNull(message = "null id")
    @NotBlank(message = "empty id")
    @Email( message = "Your email must have an email format")
    @Column(name = "user_id", length = 50)
    private String user_id;

    @NotNull( message = "null password")
    @NotBlank( message = "password empty")
    @Size( max=30, min=6)
    @Column( name = "password", length = 50)
    private String password;


    @NotNull( message = "null name")
    @NotBlank( message = "empty name")
    @Column( name = "last_name", length = 50)
    private String last_name;

    @NotNull( message = "null first name")
    @NotBlank( message = "emtpy first name")
    @Column( name="first_name", length=50)
    private String first_name;

    //Ticket generated by server to check whether the user is connected
    //private String sessionId;

    // Every strawpoll the user participated to
    //@ManyToMany
    //private List<Strawpoll> strawpollList;

    //@OneToMany
    //private List<Suggestion> suggestionList;

    public User() {
        super();
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String toString()
    {
        String res = "";
        res += "idUser : "+this.user_id+" password :"+this.password + " name: "+ this.last_name + " firstname: "+this.first_name;
        return res;
    }
}
