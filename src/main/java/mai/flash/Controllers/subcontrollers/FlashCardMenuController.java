package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mai.flash.Controllers.MainController;
import mai.flash.domain.Card;
import mai.flash.repositories.CardRepository;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class FlashCardMenuController implements Initializable {


    @FXML
    private Label deckName;
    @FXML
    private GridPane flashPane;
    @FXML
    private VBox mainBox;
    @FXML
    private Label amountUnfinishedNew;
    @FXML
    private Label newCardsAmount;
    @FXML
    private Label reviewAmount;
    @FXML
    private TextField newAmountInput;
    @FXML
    private Button studyButton;

    private final Date date = Date.valueOf(LocalDate.now());

    @Autowired
    private MainController mainController;
    @Autowired
    private SceneSwitcher sceneSwitcher;
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckName.setText(mainController.getSelectedDeck());
        setNewCardsNumber();
        setReviewCardsNumber();
        setAmountUnfinishedNew();
        refreshMenu();
    }

    @FXML
    private void refreshMenu(){

        if(cardRepository.findByDeck_NameAndReviewDateIsLessThanEqualAndCardStatus(getDeckName(),date, "review").size() == 0 && cardRepository.findByCardStatusAndDeck_Name("NewStudy" , getDeckName()).size() == 0){
            mainBox.getChildren().remove(1);
            mainBox.getChildren().add(new Label("All cards of this deck have been reviewed"));
        }else{
            mainBox.getChildren().remove(1);
            mainBox.getChildren().add(studyButton);
        }

    }

    @FXML
    private void setNewCardsNumber(){
        newCardsAmount.setText(String.valueOf(cardRepository.findByCardStatusAndDeck_Name("NewStudy", getDeckName()).size()));
    }

    @FXML
    private void setReviewCardsNumber(){
        reviewAmount.setText(String.valueOf(cardRepository.findByDeck_NameAndReviewDateIsLessThanEqualAndCardStatus(getDeckName(),date, "review").size()));
    }

    @FXML
    private void setAmountUnfinishedNew(){
        amountUnfinishedNew.setText(String.valueOf(cardRepository.findByCardStatusAndDeck_Name("new", getDeckName()).size()));
    }

    @FXML
    public void newCards(){
        saveNewCard();
        newAmountInput.setText(null);
        setNewCardsNumber();
        setAmountUnfinishedNew();
        refreshMenu();
    }

    @FXML
    private void startStudy() throws IOException {
        HBox displayBox = (HBox)flashPane.getParent();

        displayBox.getChildren().remove(flashPane);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.FLASHVIEW));
    }

    @FXML
    private void addCards() throws IOException {
        HBox displayBox = (HBox)flashPane.getParent();

        displayBox.getChildren().remove(flashPane);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.CARD));
        displayBox.setHgrow(displayBox.getChildren().get(1), Priority.ALWAYS);
    }

    private void saveNewCard(){
        List<Card> newCards = cardRepository.getPagedCards("new",getDeckName(),PageRequest.of(0, Integer.parseInt(newAmountInput.getText())));
        newCards.forEach(c -> c.setCardStatus("NewStudy"));
        cardRepository.saveAll(newCards);
    }

    public String getDeckName() {
        return deckName.getText();
    }
}


