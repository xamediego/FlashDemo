package mai.flash.domain;

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

    //vals for smaller sub tests, for normal study the values in the parent deck are used
    private double punishmentFactor;
    private double easyMultiplier;
    private double goodMultiplier;

    private Date reviewDate;

    @OneToMany
    @JoinColumn(name = "deckgroup_id")
    private List<Card> cardList;

    @ManyToOne
    private Deck deck;

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

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
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

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", punishmentFactor=" + punishmentFactor +
                ", easyMultiplier=" + easyMultiplier +
                ", goodMultiplier=" + goodMultiplier +
                ", reviewDate=" + reviewDate +
                ", deck=" + deck +
                '}';
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
