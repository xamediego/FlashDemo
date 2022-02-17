package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mai.flash.domain.Card;
import mai.flash.repositories.CardRepository;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import mai.flash.view.stage.FxmlView;
import mai.flash.view.stage.StageEvent;
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
    private HBox flashBox;
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

    private SceneSwitcher sceneSwitcher;

    private final Date date = Date.valueOf(LocalDate.now());

    private final DeckViewController deckViewController;



    @Autowired
    private CardRepository cardRepository;

    private ApplicationContext context;

    @Autowired
    public FlashCardMenuController(SceneSwitcher sceneSwitcher, DeckViewController deckViewController, ApplicationContext context) {
        this.sceneSwitcher = sceneSwitcher;
        this.deckViewController = deckViewController;
        this.context = context;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Current date: " + date);
        deckName.setText(this.deckViewController.getSelectedDeck());
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
    private void startStudy() throws IOException {
        HBox displayBox = (HBox)flashBox.getParent();

        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(this.sceneSwitcher.getNode(FxmlParts.FLASHVIEW));
    }

    @FXML
    public void newCards(){
        List<Card> newCards = cardRepository.getPagedCards("new",getDeckName(),PageRequest.of(0, Integer.parseInt(newAmountInput.getText())));
        newCards.forEach(c -> c.setCardStatus("NewStudy"));
        cardRepository.saveAll(newCards);
        newAmountInput.setText(null);
        setNewCardsNumber();
        setAmountUnfinishedNew();
        refreshMenu();
    }

    @FXML
    private void addCards(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        context.publishEvent(new StageEvent(stage, FxmlView.CARD));
    }

    public String getDeckName() {
        return deckName.getText();
    }

    public Date getCurrentDate() {
        return date;
    }
}


