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

    @OneToMany
    @JoinColumn(name = "deck_id")
    private List<Card> cardList;

    private String name;

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
