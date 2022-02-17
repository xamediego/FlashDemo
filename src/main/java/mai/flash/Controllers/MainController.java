package mai.flash.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import mai.flash.domain.Card;
import mai.flash.domain.CardEntry;
import mai.flash.domain.Deck;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckRepository;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable{

    private final SceneSwitcher sceneSwitcher;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;

    @FXML
    private HBox displayBox;

    public MainController(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;
    }

    @FXML
    private void getDeckView() throws IOException {
        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.DECK));
    }

    @FXML
    private void getDataView() throws IOException {
        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.DATA));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Deck count" + this.deckRepository.count());
        System.out.println("Card count" + this.cardRepository.count());
        System.out.println("Entry count" + this.cardEntryRepository.count());

        //Values below should only be uncommented if testing stuff, when adding new values to the entity itself bes sure to delete the data object in the app folder
/*
        this.cardEntryRepository.deleteAll();
        this.cardRepository.deleteAll();
        this.deckRepository.deleteAll();

        List<CardEntry> cardValues = new ArrayList<>();

        cardValues.add(new CardEntry("To eat"));
        cardValues.add(new CardEntry("1. to drink; to gulp; to swallow; to take (medicine)"));
        cardValues.add(new CardEntry("1. to kill; to slay; to murder; to slaughter"));
        cardValues.add(new CardEntry("1. coming alongside a pier, quay, etc.; reaching land (of a boat)"));
        cardValues.add(new CardEntry("1. to smear with blood; to kill"));
        cardValues.add(new CardEntry("1. to read"));

        this.cardEntryRepository.saveAll(cardValues);

        Deck japDeck = new Deck("Jap Core");

        this.deckRepository.save(japDeck);

        List<Card> cardSet = new ArrayList<>();
        cardSet.add(new Card("食べる",japDeck));
        cardSet.get(0).getCardEntryList().add(cardValues.get(0));
        cardSet.add(new Card("飲む",japDeck));
        cardSet.get(1).getCardEntryList().add(cardValues.get(1));
        cardSet.add(new Card("殺す",japDeck));
        cardSet.get(2).getCardEntryList().add(cardValues.get(2));
        cardSet.add(new Card("接岸",japDeck));
        cardSet.get(3).getCardEntryList().add(cardValues.get(3));
        cardSet.add(new Card("釁る",japDeck));
        cardSet.get(4).getCardEntryList().add(cardValues.get(4));
        cardSet.add(new Card("読む",japDeck));
        cardSet.get(5).getCardEntryList().add(cardValues.get(5));

        this.cardRepository.saveAll(cardSet);

        cardValues.get(0).setCard(cardSet.get(0));
        cardValues.get(1).setCard(cardSet.get(1));
        cardValues.get(2).setCard(cardSet.get(2));
        cardValues.get(3).setCard(cardSet.get(3));
        cardValues.get(4).setCard(cardSet.get(4));
        cardValues.get(5).setCard(cardSet.get(5));


        this.deckRepository.save(japDeck);
        this.cardEntryRepository.saveAll(cardValues);
        this.cardRepository.saveAll(cardSet);
*/

    }
}
