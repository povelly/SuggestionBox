package com.sorbonne.safetyline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceDTO {
	
	private int idChoice;
	private int idStrawpoll;
	private String content;
	private int votersCount;
	
}
