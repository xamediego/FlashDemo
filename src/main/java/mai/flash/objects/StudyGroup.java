package mai.flash.objects;

import lombok.Data;
import mai.flash.domain.Card;
import mai.flash.domain.CardEntry;
import mai.flash.domain.Deck;
import mai.flash.domain.DeckGroup;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
Used in the DeckFlashView for studying, works as a temp DTO
 */

@Data
public class StudyGroup {

    private List<Card> cardList;

    private List<CardEntry> cardEntryList;

    private List<Card> finishedList;

    private Deck deck;

    private DeckGroup deckGroup;

    public StudyGroup(List<Card> cardList, Deck deck, DeckGroup deckGroup, List<CardEntry> cardEntryList) {
        this.deck = deck;
        this.deckGroup = deckGroup;
        this.cardList = setBothLists(cardList);

        this.cardEntryList = cardEntryList;
    }

    public List<Card> setBothLists(List<Card> cardList){
        this.finishedList = new ArrayList<>();
        this.cardList = new ArrayList<>();
        if(deckGroup.isAlreadyStarted()){
            cardList.stream().filter(c -> !c.isFinishedLabel()).forEach(card -> this.cardList.add(card));
            cardList.stream().filter(Card::isFinishedLabel).forEach(card -> this.finishedList.add(card));
        }else{
            deckGroup.setAlreadyStarted(true);
            this.cardList = cardList;
        }
        return this.cardList;
    }

    public long getUnfinishedNumber(){
        return cardList.stream().filter(c -> !c.isGoodLabel() && !c.isHardLabel()).count();
    }

    public long getReviewingNumber(){
        return cardList.stream().filter(c -> c.isGoodLabel() || c.isHardLabel()).count();
    }

    public void easyAnswer(){
        this.cardList.get(0).setCardInterval((int)Math.round(this.cardList.get(0).getCardInterval() * this.cardList.get(0).getEasinessFactor() * deck.getGoodMultiplier() * deck.getEasyMultiplier()));
        this.cardList.get(0).setFinishedLabel(true);

        this.finishedList.add(this.cardList.remove(0));
        System.out.println("Removed from cardlist: " + this.cardList.size() + " finished: " + this.finishedList.size());
        setAverageGroupInterval();
    }

    public void goodAnswer(){

        if(!this.cardList.get(0).isGoodLabel()){
            this.cardList.get(0).setGoodLabel(true);
            this.cardList.add(this.cardList.remove(0));
        }else{
            this.cardList.get(0).setCardInterval((int)Math.round(this.cardList.get(0).getCardInterval() * this.cardList.get(0).getEasinessFactor() * deck.getGoodMultiplier()));
            this.cardList.get(0).setGoodLabel(false);
            this.cardList.get(0).setFinishedLabel(true);
            this.cardList.get(0).setEasinessFactor(this.cardList.get(0).getEasinessFactor() * 1.1);
            this.finishedList.add(this.cardList.remove(0));
            System.out.println("Removed from cardlist: " + this.cardList.size() + " finished: " + this.finishedList.size());
            setAverageGroupInterval();
        }

    }

    public void hardAnswer(){

        this.cardList.get(0).setHardLabel(true);
        this.cardList.get(0).setGoodLabel(false);
        this.cardList.get(0).setEasinessFactor(this.cardList.get(0).getEasinessFactor() * deck.getPunishmentFactor());

        if(this.cardList.size() >= 7){this.cardList.add(6,this.cardList.remove(0));}
        else{
            this.cardList.add(this.cardList.remove(0));
        }
    }

    public List<Card> getAllCards(){
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(cardList);
        allCards.addAll(finishedList);
        return allCards;
    }

    private void setAverageGroupInterval(){
        double newInterval = 0;
        newInterval += this.cardList.stream().mapToDouble(Card::getCardInterval).average().orElse(0);
        newInterval += this.finishedList.stream().mapToDouble(Card::getCardInterval).average().orElse(0);
        System.out.println(newInterval);
        deckGroup.setGroupInterval((int)Math.round(newInterval));
        if(this.cardList.isEmpty()){
            setNewGroupDate();
        }
    }

    private void setNewGroupDate(){
        Date date = new Date(TimeUnit.DAYS.toMillis((long)deckGroup.getGroupInterval()) + System.currentTimeMillis());
        System.out.println(date);
        deckGroup.setGroupDate(date);
        deckGroup.setAlreadyStarted(false);
    }

}
