package mai.flash.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Deck deck;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id")
    private List<CardEntry> cardEntryList = new ArrayList<>();

    @ManyToOne
    private DeckGroup group;

    @Column(length=1000)
    private String keyValue;

    private Date reviewDate;

    //Card can be new - NewLearn - Learning - Graduated
    private String cardStatus;

    private double easinessFactor = (float)2.4;

    private int cardInterval = 1;

    //Used in group review
    private boolean hardLabel;

    private boolean goodLabel;

    private boolean finishedLabel;

    public Card() {
    }

    public Card(String keyValue) {
        this.keyValue = keyValue;
    }

    public Card(String keyValue, Deck deck) {
        this(keyValue);
        this.deck = deck;
    }

    //Used to update cards
    public Card(String keyValue, List<CardEntry> cardEntryList, String cardStatus, Deck deck) {
        this(keyValue,deck);
        this.cardStatus = cardStatus;
    }

    public Card(Long id, Deck deck, String keyValue, Date reviewDate, String cardStatus, float easinessFactor, int cardInterval) {
        this.id = id;
        this.deck = deck;
        this.keyValue = keyValue;
        this.reviewDate = reviewDate;
        this.cardStatus = cardStatus;
        this.easinessFactor = easinessFactor;
        this.cardInterval = cardInterval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<CardEntry> getCardEntryList() {
        return cardEntryList;
    }

    public void setCardEntryList(List<CardEntry> cardEntryList) {
        this.cardEntryList = cardEntryList;
    }

    public DeckGroup getGroup() {
        return group;
    }

    public void setGroup(DeckGroup group) {
        this.group = group;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public double getEasinessFactor() {
        return easinessFactor;
    }

    public void setEasinessFactor(double easinessFactor) {
        this.easinessFactor = easinessFactor;
    }

    public int getCardInterval() {
        return cardInterval;
    }

    public void setCardInterval(int cardInterval) {
        this.cardInterval = cardInterval;
    }

    public boolean isHardLabel() {
        return hardLabel;
    }

    public void setHardLabel(boolean hardLabel) {
        this.hardLabel = hardLabel;
    }

    public boolean isGoodLabel() {
        return goodLabel;
    }

    public void setGoodLabel(boolean goodLabel) {
        this.goodLabel = goodLabel;
    }

    public boolean isFinishedLabel() {
        return finishedLabel;
    }

    public void setFinishedLabel(boolean finishedLabel) {
        this.finishedLabel = finishedLabel;
    }

    public void addSubValue(String value){
        this.cardEntryList.add(new CardEntry(value, this));
    }

    public void resetValues(){
        this.easinessFactor = (float)2.40;
        this.cardInterval = 1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
