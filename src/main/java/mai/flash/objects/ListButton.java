package mai.flash.objects;

import javafx.scene.control.Button;
import mai.flash.domain.DeckGroup;

import java.util.Objects;

public class ListButton {

    private Button mainButton;

    private DeckGroup deckGroup;

    public ListButton(DeckGroup deckGroup) {
        this.deckGroup = deckGroup;
        this.mainButton = createButton();
    }

    public Button createButton(){
        Button button = new Button();
        button.setPrefSize(50,20);
        button.setText("List " + this.deckGroup.getGroupNumber());
        return button;
    }


    public Button getMainButton() {
        return mainButton;
    }

    public void setMainButton(Button mainButton) {
        this.mainButton = mainButton;
    }

    public DeckGroup getDeckGroup() {
        return deckGroup;
    }

    public void setDeckGroup(DeckGroup deckGroup) {
        this.deckGroup = deckGroup;
    }
}
