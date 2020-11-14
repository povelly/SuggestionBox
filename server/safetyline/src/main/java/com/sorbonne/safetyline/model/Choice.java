package com.sorbonne.safetyline.model;
import javax.persistence.*;
import java.util.List;

@Entity
@Table( name="choice", schema = "safetyline")
public class Choice
{
    @Id
    @Column(name = "choice_id", length=11)
    private int choice_id;
    @Column(name = "voters_count", length=11)
    private int voter_count;
    @Column( name = "strawpoll_id", length=11)
    private int strawpoll_id;
    @Column(name = "choice_content", length=65000)
    private String choice_content;
    @ManyToOne(targetEntity = Strawpoll.class)
    @JoinColumn(name = "strawpoll_id", insertable = false, updatable = false)
    private Strawpoll strawpoll;


    public int getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(int choice_id) {
        this.choice_id = choice_id;
    }

    public int getVoter_count() {
        return voter_count;
    }

    public void setVoter_count(int voter_count) {
        this.voter_count = voter_count;
    }

    public int getStrawpoll_id() {
        return strawpoll_id;
    }

    public void setStrawpoll_id(int strawpoll_id) {
        this.strawpoll_id = strawpoll_id;
    }

    public String getChoice_content() {
        return choice_content;
    }

    public void setChoice_content(String choice_content) {
        this.choice_content = choice_content;
    }

    public Strawpoll getStrawpoll() {
        return strawpoll;
    }

    public void setStrawpoll(Strawpoll strawpoll) {
        this.strawpoll = strawpoll;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    private User user;


}
