package com.sorbonne.safetyline.model;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name="User", schema = "safetyline")
@NamedQuery(name= "User.getAll", query = "select user from User user")
public class User
{
    @NotNull( message = "null password")
    @NotBlank( message = "password empty")
    @Size( max=30, min=6)
    @Column( name = "password", length = 50)
    private String password;

    @NotNull(message = "null id")
    @NotBlank(message = "empty id")
    @Email( message = "Your email must have an email format")
    @Column(name = "id_user", length = 50)
    private String id_user;

    @NotNull( message = "null name")
    @NotBlank( message = "empty name")
    @Column( name = "name", length = 50)
    private String name;

    @NotNull( message = "null first name")
    @NotBlank( message = "emtpy first name")
    @Column( name="first_name", length=50)
    private String firstName;

    //Ticket generated by server to check whether the user is connected
    private String id_ticket;
    private static int index = 0;

    // Every strawpoll the user participated to
    @ManyToMany
    private List<Strawpoll> strawpollList;

    @OneToMany
    private List<Suggestion> suggestionList;

    public User() {
        super();
    }

    public User(String idUser, String password)
    {
        super();
        this.id_ticket = String.valueOf(index++) ;
        this.id_user = idUser;
        this.password = password;
        //suggestionList = new ArrayList<>();
        strawpollList = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;

    }
    public void setId_ticket(int id_ticket) {
        this.id_ticket = String.valueOf(id_ticket);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setId_user(String id) {
        this.id_user = id;
    }

    @Id
    public String getId_user() {
        return id_user;
    }
    public String toString()
    {
        String res = "";
        res += "idUser : "+this.id_user+" password :"+this.password ;
        return res;
    }
}
