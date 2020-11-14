package com.sorbonne.safetyline.model;


import org.hibernate.validator.constraints.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name="user", schema = "safetyline")
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
    @Column( name = "password", length = 40)
    private String password;


    @NotNull( message = "null name")
    @NotBlank( message = "empty name")
    @Column( name = "last_name", length = 50)
    private String last_name;

    @NotNull( message = "null first name")
    @NotBlank( message = "emtpy first name")
    @Column( name="first_name", length=50)
    private String first_name;

    @OneToMany(targetEntity = Suggestion.class,
            fetch=FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @MapsId("user_id")
    @JoinColumn(name = "suggestion_author")
    private List<Suggestion> suggestionList;


    @Column( name="is_admin")
    private boolean is_admin ;

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public void setSuggestionList(List<Suggestion> suggestionList) {
        this.suggestionList = suggestionList;
    }


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

    public List<Suggestion> getSuggestionList() {
        return suggestionList;
    }

    public String toString()
    {
        String res = "";
        res += "idUser : "+this.user_id+" password :"+this.password + " name: "+ this.last_name + " firstname: "+this.first_name;
        return res;
    }
}
