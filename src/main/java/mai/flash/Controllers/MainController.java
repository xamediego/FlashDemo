package mai.flash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController{

    private final SceneSwitcher sceneSwitcher;

    @FXML
    private HBox displayBox;

    public MainController(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;
    }

    //value used to remember the deck selected in case other windows use this as a starting value
    private String selectedDeckName;
    private Long selectedDeckId;

    @FXML
    private void getDeckView() throws IOException {
        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.DECK));
    }

    @FXML
    private void getDataView() throws IOException {
        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.DATA));
    }

    @FXML
    private void getImportView() throws IOException {
        displayBox.getChildren().remove(1);
        displayBox.getChildren().add(sceneSwitcher.getNode(FxmlParts.IMPORT));
        displayBox.setHgrow(displayBox.getChildren().get(0), Priority.ALWAYS);
    }

    public String getSelectedDeckName() {
        return selectedDeckName;
    }

    public void setSelectedDeckName(String selectedDeck) {
        this.selectedDeckName = selectedDeck;
    }

    public Long getSelectedDeckId() {
        return selectedDeckId;
    }

    public void setSelectedDeckId(Long selectedDeckId) {
        this.selectedDeckId = selectedDeckId;
    }

}
