package com.sorbonne.safetyline.dto;

import java.util.Date;

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

}
