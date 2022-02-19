package mai.flash.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deck_id")
    private List<Card> cardList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deck_id")
    private List<DeckGroup> groupList;

    private boolean grouped = false;

    private String name;

    private double punishmentFactor = 0.90;
    private double easyMultiplier = 1.3;
    private double goodMultiplier = 1;

    public Deck() {
        this.cardList = new ArrayList<>();
    }

    public Deck(String name) {
        this.name = name;
        this.cardList = new ArrayList<>();
    }

    public Deck(List<Card> cardList, String name) {
        this.cardList = cardList;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isGrouped() {
        return grouped;
    }

    public void setGrouped(boolean grouped) {
        this.grouped = grouped;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck publisher = (Deck) o;
        return Objects.equals(id, publisher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
