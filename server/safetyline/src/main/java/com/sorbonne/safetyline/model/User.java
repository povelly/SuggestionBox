package com.sorbonne.safetyline.model;


import org.springframework.data.jpa.repository.Query;

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
    private String userId;

    @NotNull( message = "null password")
    @NotBlank( message = "password empty")
    @Size( max=30, min=6)
    @Column( name = "password", length = 50)
    private String password;
    
    @NotNull (message = "null is admin")
    @Column( name = "is_admin")
    private Boolean isAdmin;

    @NotNull( message = "null name")
    @NotBlank( message = "empty name")
    @Column( name = "last_name", length = 50)
    private String lastName;

    @NotNull( message = "null first name")
    @NotBlank( message = "emtpy first name")
    @Column( name="first_name", length=50)
    private String firstName;

    @OneToMany(targetEntity = Suggestion.class,
            fetch=FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @MapsId("userId")
    @JoinColumn(name = "suggestion_author")
    private List<Suggestion> suggestionList;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Suggestion> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(List<Suggestion> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public String toString()
    {
        String res = "";
        res += "idUser : "+this.userId+" password :"+this.password + " name: "+ this.lastName + " firstname: "+this.firstName;
        return res;
    }
}
