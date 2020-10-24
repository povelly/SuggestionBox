package com.sorbonne.safetyline.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table( name="Suggestions", schema = "safetyline")
public class Suggestion {
    @NotNull(message = "id_recommendation cannot be null")
    private int id_recommendation;

    @NotBlank(message = "your suggestion must contains at least one character")
    private String suggestion_content;

    private String author;

    @NotNull(message = "the date of submission of the suggestion cannot be null")
    private Date date;
    private String id;

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

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
