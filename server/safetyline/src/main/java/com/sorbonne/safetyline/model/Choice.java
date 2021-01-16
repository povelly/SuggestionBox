package com.sorbonne.safetyline.model;
import org.hibernate.annotations.WhereJoinTable;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name="choice", schema = "safetyline")
public class Choice
{
    @Id
    @Column(name = "choice_id", length=11)
    private int choiceId;
    @Column(name = "voters_count", length=11)
    private int voterCount;
    @Column( name = "strawpoll_id", length=11)
    private int strawpollId;
    @Column(name = "choice_content", length=200)
    private String choiceContent;
    @ManyToOne(targetEntity = Strawpoll.class)
    @JoinColumn(name = "strawpoll_id", insertable = false, updatable = false)
    private Strawpoll strawpoll;

//    @ManyToOne
//    @MapsId("strawpollId")
//    @JoinColumn(name = "strawpoll_id")
//    private User user;
//    @ManyToMany(mappedBy = "user_id")
//    @JoinColumn(name="user_id", referencedColumnName = "strawpoll_id"),
//
//    private List<User> users;
    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public int getVoterCount() {
        return voterCount;
    }

    public void setVoterCount(int voterCount) {
        this.voterCount = voterCount;
    }

    public int getStrawpollId() {
        return strawpollId;
    }

    public void setStrawpollId(int strawpollId) {
        this.strawpollId = strawpollId;
    }

    public String getChoiceContent() {
        return choiceContent;
    }

    public void setChoiceContent(String choiceContent) {
        this.choiceContent = choiceContent;
    }

    public Strawpoll getStrawpoll() {
        return strawpoll;
    }

    public void setStrawpoll(Strawpoll strawpoll) {
        this.strawpoll = strawpoll;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
