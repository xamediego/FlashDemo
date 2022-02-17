package mai.flash.domain;

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

    @OneToMany
    @JoinColumn(name = "deck_id")
    private List<CardEntry> cardEntryList;

    private String keyValue;

    private Date reviewDate;

    //Card can be new - learning - finished
    private String cardStatus;

    private int repetitions = 0;

    private float easinessFactor = 0;

    private int cardInterval = 0;

    private int quality = 0;

    public Card() {
        this.cardEntryList = new ArrayList<>();
    }

    public Card(String keyValue, Deck deck) {
        this.keyValue = keyValue;
        this.cardEntryList = new ArrayList<>();
        this.deck = deck;
    }


    //Used to update cards
    public Card(String keyValue, List<CardEntry> cardEntryList, String cardStatus, Deck deck) {
        this.keyValue = keyValue;
        this.cardEntryList = new ArrayList<>();
        this.cardStatus = cardStatus;
        this.deck = deck;
    }

    public void addSubValue(String value){
        this.cardEntryList.add(new CardEntry(value, this));
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

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public float getEasinessFactor() {
        return easinessFactor;
    }

    public void setEasinessFactor(float easinessFactor) {
        this.easinessFactor = easinessFactor;
    }

    public int getCardInterval() {
        return cardInterval;
    }

    public void setCardInterval(int cardInterval) {
        this.cardInterval = cardInterval;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void increaseQuality(){
        if(this.quality < 5){
            this.quality++;
        }
    }

    public void decreaseQuality(){
        if(this.quality > 5){
            this.quality--;
        }
    }

    public void resetValues(){
        this.quality = 0;
        this.cardInterval = 0;
        this.easinessFactor = 0;
        this.repetitions = 0;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", deck=" + deck +
                ", keyValue='" + keyValue + '\'' +
                ", reviewDate=" + reviewDate +
                ", cardStatus='" + cardStatus + '\'' +
                ", repetitions=" + repetitions +
                ", easinessFactor=" + easinessFactor +
                ", cardInterval=" + cardInterval +
                ", quality=" + quality +
                '}';
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
