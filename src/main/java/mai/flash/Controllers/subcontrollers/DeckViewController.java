package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mai.flash.Controllers.MainController;
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
                setSelectedStatus(l.getDeckName().getText(), l.getId());
                getFlashView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    //find other way instead of useing the getChildren().get(1) to set the Hgrow
    public void getFlashView() throws IOException {
        HBox box = (HBox)this.deckBox.getParent();

        box.getChildren().remove(this.deckBox);
        box.getChildren().add(sceneSwitcher.getNode(FxmlParts.FLASHMENU));
        box.setHgrow(box.getChildren().get(1), Priority.ALWAYS);
    }

    //Store these temps somewhere else?
    public void setSelectedStatus(String selectedDeck, Long deckId){
        mainController.setSelectedDeckName(selectedDeck);
        mainController.setSelectedDeckId(deckId);
    }
}
