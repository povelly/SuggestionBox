package com.sorbonne.safetyline.model;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Strawpoll", schema = "safetyline")
public class Strawpoll
{
    private int id_strawpoll;
    private Date creation_date;
    private Date deadline_time;
    private String title;
    private List<Choice> choices;
    private String id;

    public Strawpoll(int id_strawpoll, Date creation_date, Date deadline_time, String title, List<Choice> choices)
    {
        this.id_strawpoll = id_strawpoll;
        this.creation_date = creation_date;
        this.deadline_time = deadline_time;
        this.title = title;
        this.choices = choices;
    }

    public Strawpoll() {

    }

    public int getId_strawpoll() {
        return id_strawpoll;
    }

    public void setId_strawpoll(int id_strawpoll) {
        this.id_strawpoll = id_strawpoll;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(Date deadline_time) {
        this.deadline_time = deadline_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
