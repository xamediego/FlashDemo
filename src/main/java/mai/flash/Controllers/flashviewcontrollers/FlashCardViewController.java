package mai.flash.Controllers.flashviewcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mai.flash.domain.Card;
import mai.flash.logic.Scheduler;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
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
public class FlashCardViewController implements Initializable {

    @FXML
    private VBox answerBox;
    @FXML
    private HBox buttonBox;
    @FXML
    private Label selectedDeckLabel;
    @FXML
    private Label keyword;
    @FXML
    private Label unfinishedReviewLabel;
    @FXML
    private Label unfinishedNewLabel;
    @FXML
    private Button showButton;
    @FXML
    private HBox grFlashBox;
    @FXML
    private Label resetTime;
    @FXML
    private Label hardTime;
    @FXML
    private Label goodTime;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;

    private final Date date = Date.valueOf(LocalDate.now());

    private List<Card> currentList;

    private List<Card> finishedList;

    @Autowired
    private SceneSwitcher sceneSwitcher;
    @Autowired
    private FlashCardMenuController flashCardMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentList = new ArrayList<>();
        this.finishedList = new ArrayList<>();

        this.currentList.addAll(cardRepository.findByDeck_IdAndReviewDateIsLessThanEqual(TempDeckVal.getDeck().getId(), date));
        this.currentList.addAll(cardRepository.findByDeck_IdAndCardStatus(TempDeckVal.getDeck().getId(), "NewLearn"));

        keyword.setText(this.currentList.get(0).getKeyValue());
        buttonBox.setVisible(false);
        setCounters();
    }

    private void setCounters(){
        unfinishedNewLabel.setText(String.valueOf(this.currentList.stream().filter(s -> s.getCardStatus().equals("NewLearn")).count()));
        unfinishedReviewLabel.setText(String.valueOf(this.currentList.stream().filter(s -> s.getCardStatus().equals("Learning")).count()));
    }

    @FXML
    private void showAnswer(){

        answerBox.getChildren().clear();
        buttonBox.setVisible(true);
        setButtonTime();

        cardEntryRepository.findByCard_Id(this.currentList.get(0).getId()).forEach(e -> answerBox.getChildren().add(new Label(e.getEntryValue())));

    }

    private void setButtonTime(){
        this.goodTime.setText(String.valueOf(Scheduler.getAmountOfDaysPlus(this.currentList.get(0), "Good")));
        this.hardTime.setText(String.valueOf(Scheduler.getAmountOfDaysPlus(this.currentList.get(0), "Hard")));
    }

    @FXML
    private void goodAnswer(){
        checkIfNew();

        this.currentList.get(0).setReviewDate(Scheduler.getNewSchedule(this.currentList.get(0), "Good"));

        cardRepository.save(this.currentList.remove(0));
        resetScreen();
    }

    @FXML
    private void hard(){
        checkIfNew();

        this.currentList.get(0).setReviewDate(Scheduler.getNewSchedule(this.currentList.get(0), "Hard"));

        cardRepository.save(this.currentList.remove(0));
        resetScreen();
    }

    @FXML
    private void reset(){
        this.currentList.get(0).resetValues();
        this.currentList.add(this.currentList.remove(0));
        resetScreen();
    }

    private void checkIfNew(){
        if(this.currentList.get(0).getReviewDate() == null){
            this.currentList.get(0).setReviewDate(date);
            this.currentList.get(0).setCardStatus("Learning");
        }
    }

    private void resetScreen(){
        if(!this.currentList.isEmpty()) {

            buttonBox.setVisible(false);
            answerBox.getChildren().clear();
            answerBox.getChildren().add(showButton);
            keyword.setText(this.currentList.get(0).getKeyValue());
            setCounters();

        }else{
            try {
                HBox displayBox = (HBox)grFlashBox.getParent();
                displayBox.getChildren().remove(grFlashBox);
                displayBox.getChildren().add(this.sceneSwitcher.getNode(FxmlParts.FLASHMENU));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
