package com.sorbonne.safetyline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table( name="Suggestions", schema = "safetyline")
public class Suggestion {
    @Id
    @NotNull(message = "id_recommendation cannot be null")
    @Column( name="Id_recommendation")
    private Integer id_recommendation;

    @NotBlank(message = "your suggestion must contains at least one character")
    @Column( name="suggestion_content")
    private String suggestion_content;

    @Column( name="author")
    private String author;

    @NotNull(message = "the date of submission of the suggestion cannot be null")
    @Column( name="date")
    private Date date;


    public Suggestion(int id_recommendation, String suggestion_content, String author, Date date)
    {
        this.suggestion_content = suggestion_content;
        this.id_recommendation = id_recommendation;
        this.author = author;
        this.date = date;
    }

    public Suggestion() {

    }

    public int getId_recommendation() {
        return id_recommendation;
    }

    public void setId_recommendation(int id_recommendation) {
        this.id_recommendation = id_recommendation;
    }

    public String getSuggestion_content() {
        return suggestion_content;
    }

    public void setSuggestion_content(String suggestion_content) {
        this.suggestion_content = suggestion_content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
