package mai.flash.Controllers.flashviewcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mai.flash.domain.DeckGroup;
import mai.flash.objects.StudyGroup;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckGroupRepository;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class GroupMenuController implements Initializable{

    @FXML
    private GridPane menuPane;
    @FXML
    private Button startStudyButton;

    @Autowired
    private SceneSwitcher sceneSwitcher;
    @Autowired
    private DeckGroupRepository deckGroupRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;
    @Autowired
    private GroupDeckMenuController groupDeckMenuController;

    private DeckGroup deckGroup;

    private StudyGroup studyGroup;

    @FXML
    private void startStudy() throws IOException{
        VBox box = (VBox)this.menuPane.getParent();
        box.getChildren().remove(this.menuPane);

        box.getChildren().add(sceneSwitcher.getNode(FxmlParts.GRFLASHVIEW));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(studyGroup != null){
            saveGroupData();
            studyGroup = null;
        }

        deckGroup = groupDeckMenuController.getDeckGroup();
        studyGroup = new StudyGroup(cardRepository.findByGroup_Id(getDeckGroup().getId()), TempDeckVal.getDeck(), getDeckGroup(), cardEntryRepository.findByDeckGroup_Id(getDeckGroup().getId()));
        setDeckMenu();

    }

    public DeckGroup getDeckGroup() {
        return deckGroup;
    }

    public void setDeckGroup(DeckGroup deckGroup) {
        this.deckGroup = deckGroup;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setDeckMenu(){
        if(studyGroup.getCardList().isEmpty()){
            menuPane.getChildren().clear();
            menuPane.getChildren().add(new Label("all cards reviewed"));
        }else{
            menuPane.getChildren().clear();
            menuPane.getChildren().add(startStudyButton);
        }
    }

    public void saveGroupData(){

            deckGroupRepository.save(studyGroup.getDeckGroup());
            cardRepository.saveAll(studyGroup.getFinishedList());
            cardRepository.saveAll(studyGroup.getCardList());

    }

}
