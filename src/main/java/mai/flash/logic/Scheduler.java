package mai.flash.logic;

import mai.flash.domain.Card;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private static double interval;
    private static double easinessFactor;
    private static Card card;
    private static Date date;

    public static Date getNewSchedule(Card insertCard, String reviewState){

        card = insertCard;

        System.out.println("old: " + card.getCardInterval());

        interval = card.getCardInterval();
        easinessFactor = card.getEasinessFactor();

        setReview(reviewState);

        card.setCardInterval((int)interval);

        System.out.println("new: " + card.getCardInterval());

        card.setEasinessFactor(easinessFactor);

        return date;
    }

    public static double getAmountOfDaysPlus(Card insertCard, String reviewState) {
        card = insertCard;
        interval = card.getCardInterval();
        easinessFactor = card.getEasinessFactor();

        setReview(reviewState);

        return interval;
    }

    private static void setReview(String reviewState){
        switch (reviewState) {
            case "Hard":
                hard();
                break;
            case "Good":
                good();
                break;
            case "Easy":
                easy();
                break;
            case "Again":
                again();
                break;
        }

        Math.round(interval);

        card.setCardInterval((int)interval);

        date = new Date(TimeUnit.DAYS.toMillis((long)interval) + System.currentTimeMillis());
    }


    private static void hard(){
        interval = interval * 1.2 * 1;
        easinessFactor = card.getEasinessFactor() - 0.15;
    }

    private static void good(){
        interval = interval * easinessFactor * 1;
    }

    private static void easy(){
        interval = interval * easinessFactor * 1 * 1.3;
        easinessFactor = card.getEasinessFactor() + 0.15;
    }

    private static void again(){
        card.setCardInterval((int)(interval * 0.8));
        easinessFactor = card.getEasinessFactor() - 0.20;
    }


}
