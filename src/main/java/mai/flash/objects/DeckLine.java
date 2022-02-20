package mai.flash.objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import mai.flash.domain.Deck;

import java.util.Objects;

public class DeckLine {

    private String unfinishedNew;
    private String reviews;

    private Button settingsButton;

    private HBox deckBox;

    private Label deckName;


    private Deck deck;

    public DeckLine(Deck deck, long unfinishedNew, long reviews) {
        this.unfinishedNew = String.valueOf(unfinishedNew);
        this.reviews = String.valueOf(reviews);
        this.deckBox = new HBox();
        this.settingsButton = new Button();
        this.deck = deck;
        constructDeckLine();
    }

    public HBox constructDeckLine(){

        deckName = new Label(deck.getName());
        deckName.setPadding(new Insets(0,0,0,10));

        Label isGroupedLabel = new Label();
        isGroupedLabel.setPadding(new Insets(0,0,0,10));

        Pane buffPane = new Pane();
        buffPane.setPrefSize(0,45);

        HBox nameBox = new HBox(deckName,buffPane, isGroupedLabel);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setPrefSize(390,25);

        Label unfinishedNew = new Label(this.unfinishedNew);
        unfinishedNew.setTextFill(Color.rgb(58,171,48));

        Label separator = new Label("|");

        Label reviews = new Label(this.reviews);
        reviews.setTextFill(Color.rgb(218,47,47));

        HBox counterBox = new HBox(unfinishedNew,separator,reviews);
        counterBox.setSpacing(2);
        counterBox.setPrefSize(100,25);
        counterBox.setMaxSize(100,25);
        counterBox.setAlignment(Pos.CENTER);

        this.settingsButton.setMinSize(20,20);
        this.settingsButton.setMaxSize(20,20);
        this.settingsButton.setPrefSize(20,20);

        this.deckBox.getChildren().addAll(nameBox,counterBox,this.settingsButton);
        this.deckBox.setAlignment(Pos.CENTER_LEFT);
        this.deckBox.setSpacing(2);
        this.deckBox.setPadding(new Insets(0,0,0,10));
        this.deckBox.setPrefSize(600,46);

        return this.deckBox;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Label getDeckName() {
        return deckName;
    }

    public Deck getDeck() {
        return deck;
    }

    public HBox getDeckBox() {
        return this.deckBox;
    }

}
