package mai.flash.logic;

import mai.flash.domain.Card;
import mai.flash.domain.Deck;

import java.sql.Date;
import java.util.concurrent.TimeUnit;


public class Reviewer {

    public static void easyCard(Card card, Deck deck){

        double interval = card.getCardInterval();
        interval = interval + card.getEasinessFactor() * deck.getGoodMultiplier() * deck.getEasyMultiplier();
        Math.round(interval);

        card.setCardInterval((int)interval);

        card.setReviewDate(new Date(TimeUnit.DAYS.toMillis((long)interval) + System.currentTimeMillis()));
    }

    public static void goodCard(Card card, Deck deck){
        double interval = card.getCardInterval();

        interval = interval + card.getEasinessFactor() * deck.getGoodMultiplier();
        Math.round(interval);

        card.setCardInterval((int)interval);

        card.setReviewDate(new Date(TimeUnit.DAYS.toMillis((long)interval) + System.currentTimeMillis()));
    }

}
