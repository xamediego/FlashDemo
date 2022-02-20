package mai.flash.Controllers.flashviewcontrollers;

/*
Class used to temp est and get the selected deck name and id
Find a way to remove this please?
 */

import mai.flash.domain.Deck;

public class TempDeckVal {

    private static Deck deck;

    private static Long selectedDeckId;

    public static Deck getDeck() {
        return deck;
    }

    public static void setDeck(Deck deck) {
        TempDeckVal.deck = deck;
    }
}
