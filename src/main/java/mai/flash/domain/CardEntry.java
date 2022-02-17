package mai.flash.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String entryValue;

    @ManyToOne
    private Card card;


    public CardEntry() {
    }

    public CardEntry(String entryValue) {
        this.entryValue = entryValue;
    }

    public CardEntry(String entryValue, Card card) {
        this.entryValue = entryValue;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntryValue() {
        return entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "CardEntry{" +
                "id=" + id +
                ", entryValue='" + entryValue + '\'' +
                ", card=" + card +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardEntry cardEntry = (CardEntry) o;
        return Objects.equals(id, cardEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
