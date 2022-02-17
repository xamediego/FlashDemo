package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mai.flash.Controllers.MainController;
import mai.flash.domain.Deck;
import mai.flash.objects.DeckLine;
import mai.flash.repositories.DeckRepository;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DeckViewController implements Initializable {

    @FXML
    private VBox deckList;
    @FXML
    private VBox deckBox;

    private List<DeckLine> lineList;

    private final SceneSwitcher sceneSwitcher;

    private Date date = Date.valueOf(LocalDate.now());

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private MainController mainController;

    public DeckViewController(SceneSwitcher sceneSwitcher) {
        this.lineList = new ArrayList();
        this.sceneSwitcher = sceneSwitcher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*want to find way to not have to use these and not have to use something weird for creating the decklines
            maybe initialize the decklines when the maincontroller initializes
         */
        lineList.clear();
        deckList.getChildren().clear();

        createDeckLines();
    }

    public void createDeckLines(){

        deckRepository.findAll().forEach(deck -> lineList.add(new DeckLine(deck.getName()
                , deckRepository.getCardStatusSize(deck.getName(), "NewStudy")
                , deckRepository.getCardStatusDateLessSize(deck.getName(), "review", date),
                deck.getId())));

        lineList.forEach(l -> deckList.getChildren().add(l.getDeckBox()));

        lineList.forEach(l -> l.getDeckName().setOnMouseClicked(mouseEvent -> {
            try {
                getFlashView(l.getDeckName().getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }

    //Changes menu to the flash menu of the selected deck and also passes the selected deck name to the MainController and local string, might merge these two later to centralize it at the main
    public void getFlashView(String selectedDeck) throws IOException {

        mainController.setSelectedDeck(selectedDeck);

        HBox box = (HBox)this.deckBox.getParent();

        box.getChildren().remove(1);
        box.getChildren().add(sceneSwitcher.getNode(FxmlParts.FLASHMENU));
        box.setHgrow(box.getChildren().get(1), Priority.ALWAYS);
    }
}
