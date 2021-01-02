package com.sorbonne.safetyline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table( name="suggestion", schema = "safetyline")
public class Suggestion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column( name="suggestion_id")
    private Integer suggestionId;

    @NotBlank(message = "your suggestion must contains at least one character")
    @Column( name="suggestion_content")
    private String suggestionContent;


    @NotNull(message = "the date of submission of the suggestion cannot be null")
    @Column( name="suggestion_creation_date")
    private Date suggestionCreationDate;

    @Column( name="suggestion_author")
    private String suggestionAuthor;


    public Suggestion() {

    }

    public Integer getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(Integer suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSuggestionContent() {
        return suggestionContent;
    }

    public void setSuggestionContent(String suggestionContent) {
        this.suggestionContent = suggestionContent;
    }

    public Date getSuggestionCreationDate() {
        return suggestionCreationDate;
    }

    public void setSuggestionCreationDate(Date suggestionCreationDate) {
        this.suggestionCreationDate = suggestionCreationDate;
    }

    public String getSuggestionAuthor() {
        return suggestionAuthor;
    }

    public void setSuggestionAuthor(String suggestionAuthor) {
        this.suggestionAuthor = suggestionAuthor;
    }

    public String toString()
    {
        return "suggestion "+this.suggestionId
                +" created the "+ this.suggestionCreationDate +
                " by "+ this.suggestionAuthor +
                "content: "+this.suggestionContent;
    }
}
