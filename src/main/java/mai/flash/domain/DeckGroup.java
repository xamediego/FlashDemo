package mai.flash.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class DeckGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean alreadyStarted;

    private int groupInterval;

    private Date groupDate;

    //vals for smaller sub tests, for normal study the values in the parent deck are used
    private double punishmentFactor;
    private double easyMultiplier;
    private double goodMultiplier;

    private Date reviewDate;

    @OneToMany
    @JoinColumn(name = "group_id")
    private List<Card> cardList;

    @OneToMany
    @JoinColumn
    private List<CardEntry> cardEntryList;

    @ManyToOne
    private Deck deck;

    private int groupNumber;

    public DeckGroup() {
        this.cardList = new ArrayList<>();
    }

    public DeckGroup(Deck deck) {
        this();
        this.deck = deck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAlreadyStarted() {
        return alreadyStarted;
    }

    public void setAlreadyStarted(boolean alreadyStarted) {
        this.alreadyStarted = alreadyStarted;
    }

    public double getPunishmentFactor() {
        return punishmentFactor;
    }

    public void setPunishmentFactor(double punishmentFactor) {
        this.punishmentFactor = punishmentFactor;
    }

    public double getEasyMultiplier() {
        return easyMultiplier;
    }

    public void setEasyMultiplier(double easyMultiplier) {
        this.easyMultiplier = easyMultiplier;
    }

    public double getGoodMultiplier() {
        return goodMultiplier;
    }

    public void setGoodMultiplier(double goodMultiplier) {
        this.goodMultiplier = goodMultiplier;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getGroupInterval() {
        return groupInterval;
    }

    public void setGroupInterval(int groupInterval) {
        this.groupInterval = groupInterval;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public List<CardEntry> getCardEntryList() {
        return cardEntryList;
    }

    public void setCardEntryList(List<CardEntry> cardEntryList) {
        this.cardEntryList = cardEntryList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckGroup group = (DeckGroup) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
