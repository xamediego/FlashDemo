package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mai.flash.Controllers.BadValueStorageClass;
import mai.flash.Controllers.MainController;
import mai.flash.domain.Card;
import mai.flash.events.deldeckevent.DeleteDeckEvent;
import mai.flash.repositories.CardRepository;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    private VBox nameAndButtonBox;
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
    @FXML
    private VBox studyTypeBox;
    @FXML
    private Button setGroupedButton;
    @FXML
    private VBox singlesBox;

    private final Date date = Date.valueOf(LocalDate.now());

    @Autowired
    private MainController mainController;
    @Autowired
    private SceneSwitcher sceneSwitcher;
    @Autowired
    private CardRepository cardRepository;

    private final ApplicationContext applicationContext;

    public FlashCardMenuController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckName.setText(BadValueStorageClass.getSelectedDeckName());
        setNewCardsNumber();
        setReviewCardsNumber();
        setAmountUnfinishedNew();
        refreshMenu();
    }

    @FXML
    private void refreshMenu(){

        if(cardRepository.findByDeck_IdAndCardStatusAndReviewDateIsLessThanEqual(BadValueStorageClass.getSelectedDeckId(),"Learning",date).size() == 0 && cardRepository.findByDeck_IdAndCardStatus(BadValueStorageClass.getSelectedDeckId(),"NewLearn" ).size() == 0){
            nameAndButtonBox.getChildren().clear();
            nameAndButtonBox.getChildren().add(new Label("All cards of this deck have been reviewed"));
        }else{
            nameAndButtonBox.getChildren().clear();
            nameAndButtonBox.getChildren().add(studyButton);
        }

    }

    @FXML
    private void setNewCardsNumber(){
        newCardsAmount.setText(String.valueOf(cardRepository.findByDeck_IdAndCardStatus(BadValueStorageClass.getSelectedDeckId(),"NewLearn").size()));
    }

    @FXML
    private void setReviewCardsNumber(){
        reviewAmount.setText(String.valueOf(cardRepository.findByDeck_IdAndCardStatusAndReviewDateIsLessThanEqual(BadValueStorageClass.getSelectedDeckId(),"Learning",date).size()));
    }

    @FXML
    private void setAmountUnfinishedNew(){
        amountUnfinishedNew.setText(String.valueOf(cardRepository.findByDeck_IdAndCardStatus(BadValueStorageClass.getSelectedDeckId(),"new").size()));
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
        List<Card> newCards = cardRepository.FindPageable_IdAndCardStatus(BadValueStorageClass.getSelectedDeckId(),"new",PageRequest.of(0, Integer.parseInt(newAmountInput.getText())));
        newCards.forEach(c -> c.setCardStatus("NewLearn"));
        cardRepository.saveAll(newCards);
    }

    @FXML
    private void getGroupedView() throws IOException {
        if(setGroupedButton.getText().equals("Grouped")) {
            studyTypeBox.getChildren().clear();
            studyTypeBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.GROUPDECK));
            setGroupedButton.setText("Singles");
        }else{
            studyTypeBox.getChildren().clear();
            studyTypeBox.getChildren().add(singlesBox);
            setGroupedButton.setText("Grouped");
        }
    }

    @FXML
    private void deleteDeck(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Deck");
        alert.setHeaderText("You're about to delete the current deck");
        alert.setContentText("Are you sure you want to delete this deck?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            applicationContext.publishEvent(new DeleteDeckEvent(BadValueStorageClass.getSelectedDeckId()));
            setClearMenuScreen();
            setDeletedState();
        }
    }

    public void setClearMenuScreen(){
        mainBox.getChildren().clear();
    }

    private void setDeletedState(){
        Label deleteDeckLabel = new Label("Deck: " + BadValueStorageClass.getSelectedDeckName() + ", has been deleted");
        deleteDeckLabel.setFont(new Font(18));
        mainBox.getChildren().add(deleteDeckLabel);
    }


    public String getDeckName() {
        return deckName.getText();
    }
}


