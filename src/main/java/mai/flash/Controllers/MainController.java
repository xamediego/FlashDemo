package mai.flash.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
public class MainController{

    private final SceneSwitcher sceneSwitcher;

    @FXML
    private HBox displayBox;

    public MainController(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;
    }

    //value used to remember the deck selected in case other windows use this as a starting value
    private String selectedDeck;

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

    @FXML
    private void getImportView() throws IOException {
        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.IMPORT));
        displayBox.setHgrow(displayBox.getChildren().get(0), Priority.ALWAYS);
    }

    public String getSelectedDeck() {
        return selectedDeck;
    }

    public void setSelectedDeck(String selectedDeck) {
        this.selectedDeck = selectedDeck;
    }
}
