package com.sorbonne.safetyline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrawpollDTO {

    private String title;
    private String author;
    private Date expirationDate;
    private List<String> choicesContent;
    private int idStrawpoll;
    private List<ChoiceDTO> choices;
    
    /** Utilisé pour l'affichage coté front */
	public StrawpollDTO(String title, String author, Date expirationDate, int idStrawpoll, List<ChoiceDTO> choices) {
		super();
		this.title = title;
		this.author = author;
		this.expirationDate = expirationDate;
		this.idStrawpoll = idStrawpoll;
		this.choices = choices;
	}
}
