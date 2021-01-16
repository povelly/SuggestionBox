package com.sorbonne.safetyline.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "strawpoll", schema = "safetyline")
public class Strawpoll
{
    @Id
    @Column( name="strawpoll_id", length = 11)
    private int strawpollId;
    @Column( name="strawpoll_creation_date")
    @CreationTimestamp
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


    @Column(name="strawpoll_author")
    private String author;

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

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

//    public List<Choice> getChoices() {
//        return choices;
//    }
//
//    public void setChoices(List<Choice> choices) {
//        this.choices = choices;
//    }
}
