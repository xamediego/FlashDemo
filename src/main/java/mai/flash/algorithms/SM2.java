package mai.flash.algorithms;

import mai.flash.domain.Card;

import java.sql.Date;

public class SM2 {

    private static int repetitions;
    private static float easiness;
    private static int interval;

    public static Date calculateSM2Time(Card card, int quality) {

        if (quality < 0 || quality > 5) {
            // throw error here or ensure elsewhere that quality is always within 0-5
        }

        // retrieve the stored values (default values if new cards)
        repetitions = card.getRepetitions();
        easiness = card.getEasinessFactor();
        interval = card.getCardInterval();

        // easiness factor
        easiness = (float) Math.max(1.3, easiness + 0.1 - (5.0 - quality) * (0.08 + (5.0 - quality) * 0.02));

        // repetitions
        if (quality < 3) {
            repetitions = 0;
        } else {
            repetitions += 1;
        }

        // interval
        if (repetitions <= 1) {
            interval = 1;
        } else if (repetitions == 2) {
            interval = 6;
        } else {
            interval = Math.round(interval * easiness);
        }

        // next practice
        int millisecondsInDay = 60 * 60 * 24 * 1000;
        long now = System.currentTimeMillis();
        long nextPracticeDate = now + millisecondsInDay * interval;

        return new Date(nextPracticeDate);
    }

    public static Card setDateValue(Card card, int quality){
        card.setReviewDate(calculateSM2Time(card, quality));
        return card;
    }
}