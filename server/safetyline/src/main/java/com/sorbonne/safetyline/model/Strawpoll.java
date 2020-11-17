package com.sorbonne.safetyline.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "strawpoll", schema = "safetyline")
public class Strawpoll
{
    @Id
    @Column( name="strawpoll_id", length = 11)
    private int strawpoll_id;
    @Column( name="strawpoll_creation_date")
    private Date creation_date;
    @Column( name="strawpoll_expiration_date")
    private Date deadline_time;
    @Column( name="title", length = 150)
    private String title;

    @OneToMany( fetch = FetchType.EAGER,
                cascade = {
                    CascadeType.PERSIST,
                        CascadeType.MERGE
                })
    @MapsId("strawpoll_id")
    @JoinColumn(name = "strawpoll_id")
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
//@ManyToMany
    //private List<User> usersParticipated;

    public Strawpoll(int id_strawpoll, Date creation_date, Date deadline_time, String title, List<Choice> choices)
    {
        this.strawpoll_id = id_strawpoll;
        this.creation_date = creation_date;
        this.deadline_time = deadline_time;
        this.title = title;
        //this.choices = choices;
    }

    public Strawpoll() {

    }

    public int getStrawpoll_id() {
        return strawpoll_id;
    }

    public void setStrawpoll_id(int id_strawpoll) {
        this.strawpoll_id = id_strawpoll;
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

}
