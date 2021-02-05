package com.sorbonne.safetyline.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table( name="vote", schema = "safetyline")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "vote_id", length=11)
    private Integer voteId;
	
	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", insertable = true, updatable = false)
    private String userId;
	
	@OneToOne(targetEntity = Choice.class)
	@JoinColumn(name = "choice_id", insertable = true, updatable = false)
	private int choiceId;

}
