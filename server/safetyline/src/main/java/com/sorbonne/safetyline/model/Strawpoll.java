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
    private int strawpollId;
    @Column( name="strawpoll_creation_date")
    private Date creationDate;
    @Column( name="strawpoll_expiration_date")
    private Date deadlineTime;
    @Column( name="title", length = 150)
    private String title;

    @OneToMany( fetch = FetchType.EAGER,
                cascade = {
                    CascadeType.PERSIST,
                        CascadeType.MERGE
                })
    @MapsId("strawpollId")
    @JoinColumn(name = "strawpoll_id")
    private List<Choice> choices;

    public int getStrawpollId() {
        return strawpollId;
    }

    public void setStrawpollId(int strawpollId) {
        this.strawpollId = strawpollId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
