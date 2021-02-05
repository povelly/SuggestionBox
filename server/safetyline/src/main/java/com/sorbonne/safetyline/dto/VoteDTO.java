package com.sorbonne.safetyline.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoteDTO {
	
	/** Strawpoll id */
	private int id; 
	
	/** User who voted */
	private String author;
	
	/** List of choiceId */
	private List<String> reponses;

}
