package com.sorbonne.safetyline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table( name="suggestion", schema = "safetyline")
public class Suggestion {
    @Id
    @NotNull(message = "id_recommendation cannot be null")
    @Column( name="suggestion_id")
    private Integer suggestion_id;

    @NotBlank(message = "your suggestion must contains at least one character")
    @Column( name="suggestion_content")
    private String suggestion_content;


    @NotNull(message = "the date of submission of the suggestion cannot be null")
    @Column( name="suggestion_creation_date")
    private Date suggestion_creation_date;

    @Column( name="suggestion_author")
    private String suggestion_author;


    public Suggestion() {

    }
    public int getSuggestion_id() {
        return suggestion_id;
    }

    public void setSuggestion_id(int suggestion_id) {
        this.suggestion_id = suggestion_id;
    }

    public String getSuggestion_content() {
        return suggestion_content;
    }

    public void setSuggestion_content(String suggestion_content) {
        this.suggestion_content = suggestion_content;
    }

    public Date getSuggestion_creation_date() {
        return suggestion_creation_date;
    }

    public void setSuggestion_creation_date(Date suggestion_creation_date) {
        this.suggestion_creation_date = suggestion_creation_date;
    }

    public String getSuggestion_author() {
        return suggestion_author;
    }

    public void setSuggestion_author(String suggestion_author) {
        this.suggestion_author = suggestion_author;
    }
    public String toString()
    {
        return "suggestion "+this.suggestion_id
                +" created the "+ this.suggestion_creation_date +
                " by "+ this.suggestion_author +
                "content: "+this.suggestion_content;
    }
}
