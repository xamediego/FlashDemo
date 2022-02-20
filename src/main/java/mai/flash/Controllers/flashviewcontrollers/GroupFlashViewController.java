package mai.flash.Controllers.flashviewcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mai.flash.objects.StudyGroup;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckGroupRepository;
import mai.flash.repositories.DeckRepository;

import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Component
public class GroupFlashViewController implements Initializable{

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;
    @Autowired
    private DeckGroupRepository deckGroupRepository;
    @Autowired
    private GroupMenuController groupMenuController;
    @Autowired
    private SceneSwitcher sceneSwitcher;
    @FXML
    private HBox buttonBox;
    @FXML
    private VBox answerBox;
    @FXML
    private VBox flashViewBox;
    @FXML
    private Label keyWord;
    @FXML
    private Label selectedListNumber;
    @FXML
    private Button showButton;
    @FXML
    private Label unfinishedNewLabel;
    @FXML
    private Label unfinishedReviewLabel;
    @FXML
    private HBox grFlashBox;

    private StudyGroup studyGroup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studyGroup = groupMenuController.getStudyGroup();
        resetScreen();
    }

    private void setCounters(){
        unfinishedNewLabel.setText(String.valueOf(studyGroup.getUnfinishedNumber()));
        unfinishedReviewLabel.setText(String.valueOf(studyGroup.getReviewingNumber()));
    }

    @FXML
    private void showAnswer(){
        answerBox.getChildren().clear();
        studyGroup.getCardEntryList().forEach(entry -> answerBox.getChildren().add(new Label(entry.getEntryValue())));
        buttonBox.setVisible(true);

    }

    @FXML
    private void goodAnswer() {
        studyGroup.goodAnswer();
        resetScreen();
    }

    @FXML
    private void hardAnswer() {
        studyGroup.hardAnswer();
        resetScreen();
    }

    @FXML
    private void easyAnswer() {
        studyGroup.easyAnswer();
        resetScreen();
    }


    private void resetScreen(){
        if(!studyGroup.getCardList().isEmpty()){
            setCounters();
            answerBox.getChildren().clear();
            answerBox.getChildren().add(showButton);
            buttonBox.setVisible(false);
            keyWord.setText(studyGroup.getCardList().get(0).getKeyValue());
            System.out.println("Has good label: " + studyGroup.getCardList().get(0).isGoodLabel() + ",  Has hard label: " + studyGroup.getCardList().get(0).isHardLabel());
        }else{
            try {
                VBox box = (VBox)this.grFlashBox.getParent();
                box.getChildren().remove(this.grFlashBox);
                box.getChildren().add(sceneSwitcher.getNode(FxmlParts.GROUPDECK));
                groupMenuController.setDeckMenu();
                groupMenuController.saveGroupData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
