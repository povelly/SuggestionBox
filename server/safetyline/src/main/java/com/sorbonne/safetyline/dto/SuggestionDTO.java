package com.sorbonne.safetyline.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionDTO {
	
	private int id;
	private String content;
	private String author;
	// interval search begin
	private Date begin;
	// interval search end
	private Date end;

}
