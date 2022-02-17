package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    private String currentDeck;

    private Date date = Date.valueOf(LocalDate.now());

    @Autowired
    private DeckRepository deckRepository;


    public DeckViewController(SceneSwitcher sceneSwitcher) {
        this.lineList = new ArrayList();
        this.sceneSwitcher = sceneSwitcher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createDeckLines();
    }

    /*
    Creates or refreshes Decklines
    Maybe there is a better way to put this disgusting if else statement together
    but right now needs to be left like this otherwise causes a duplicate child error
     */
    public void createDeckLines(){

        if(lineList.isEmpty()) {
            deckRepository.findAll().forEach(deck -> lineList.add(new DeckLine(deck.getName()
                    , deckRepository.getCardStatusSize(deck.getName(), "NewStudy")
                    , deckRepository.getCardStatusDateLessSize(deck.getName(), "review", date),
                    deck.getId())));
        }else{
           for(Deck d : deckRepository.findAll()){
                for(DeckLine line : lineList){
                    if(!d.getId().equals(line.getId())){
                        lineList.add(new DeckLine(d.getName()
                                , deckRepository.getCardStatusSize(d.getName(), "NewStudy")
                                , deckRepository.getCardStatusDateLessSize(d.getName(), "review", date),
                                d.getId()));
                    }
                }
            }
        }

        lineList.forEach(l -> deckList.getChildren().add(l.getDeckBox()));

        lineList.forEach(l -> l.getDeckName().setOnMouseClicked(mouseEvent -> {
            try {
                getFlashView(l.getDeckName().getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }

    public void getFlashView(String selectedDeck) throws IOException {
        this.currentDeck = selectedDeck;
        HBox box = (HBox)this.deckBox.getParent();
        box.getChildren().remove(1);
        box.getChildren().add(sceneSwitcher.getNode(FxmlParts.FLASHMENU));
    }

    public String getSelectedDeck() {
        return this.currentDeck;
    }

    public VBox getDeckList() {
        return deckList;
    }
}
