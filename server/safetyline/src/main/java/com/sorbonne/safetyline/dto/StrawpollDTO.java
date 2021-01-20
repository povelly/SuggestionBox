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
    private List<String> choices;
    private int idStrawpoll;

}
