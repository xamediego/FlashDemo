package mai.flash.objects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;

public class DeckLine {

    private Long id;

    private Label deckName;
    private String unfinishedNew;
    private String reviews;

    private Button settingsButton;

    private HBox deckBox;

    private boolean isGrouped;

    public DeckLine(String deckName, long unfinishedNew, long reviews , Long id, boolean isGrouped) {
        this.deckName = new Label(deckName);
        this.unfinishedNew = String.valueOf(unfinishedNew);
        this.reviews = String.valueOf(reviews);
        this.deckBox = new HBox();
        this.settingsButton = new Button();
        this.id = id;
        this.isGrouped = isGrouped;
        constructDeckLine();
    }

    public HBox constructDeckLine(){

        this.deckName.setPadding(new Insets(0,0,0,10));

        Label isGroupedLabel = new Label();
        isGroupedLabel.setPadding(new Insets(0,0,0,10));

        Pane buffPane = new Pane();
        buffPane.setPrefSize(0,45);

        HBox nameBox = new HBox(this.deckName,buffPane, isGroupedLabel);
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

    public Label getDeckName() {
        return deckName;
    }

    public void setDeckName(Label deckName) {
        this.deckName = deckName;
    }

    public String getUnfinishedNew() {
        return unfinishedNew;
    }

    public void setUnfinishedNew(String unfinishedNew) {
        this.unfinishedNew = unfinishedNew;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(Button settingsButton) {
        this.settingsButton = settingsButton;
    }

    public HBox getDeckBox() {
        return this.deckBox;
    }

    public void setDeckBox(HBox deckBox) {
        this.deckBox = deckBox;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public void setGrouped(boolean grouped) {
        isGrouped = grouped;
    }

    @Override
    public String toString(){
        return "Deck Name: " + this.deckName.getText() +
                "\nId : " + this.id +
                "\nIs Grouped: " + this.isGrouped +
                "\nUnfinished New " + unfinishedNew +
                "\nReviews: " + this.reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckLine deckLine = (DeckLine) o;
        return Objects.equals(id, deckLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
