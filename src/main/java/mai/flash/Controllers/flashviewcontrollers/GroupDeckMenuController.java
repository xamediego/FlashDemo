package mai.flash.Controllers.flashviewcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mai.flash.domain.DeckGroup;
import mai.flash.objects.ListButton;
import mai.flash.repositories.DeckGroupRepository;
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
public class GroupDeckMenuController implements Initializable {

    @FXML
    private GridPane menuPane;

    @FXML
    private VBox listBox;

    private List<DeckGroup> groupList;

    @Autowired
    private SceneSwitcher sceneSwitcher;

    @Autowired
    private DeckGroupRepository deckGroupRepository;

    @Autowired
    private GroupMenuController groupMenuController;

    private DeckGroup deckGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupList = new ArrayList<>();
        createButtons();
    }

    @FXML
    private void createButtons(){
        List<ListButton> buttonList = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            DeckGroup in = deckGroupRepository.findByDeck_Id(TempDeckVal.getDeck().getId()).get(i);
            buttonList.add(new ListButton(in));
        }

        buttonList.forEach(b -> listBox.getChildren().add(b.getMainButton()));

        buttonList.forEach(b -> b.getMainButton().setOnAction(event -> {
            try {
                selectList(b.getDeckGroup());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    private void selectList(DeckGroup selectedGroup) throws IOException {
        this.deckGroup = selectedGroup;
        VBox box = (VBox)this.menuPane.getParent().getParent();
        box.getChildren().clear();

        box.getChildren().add(sceneSwitcher.getNode(FxmlParts.GROUPMENU));
    }

    public DeckGroup getDeckGroup() {
        return deckGroup;
    }
}
