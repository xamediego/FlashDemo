package mai.flash.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import mai.flash.Controllers.flashviewcontrollers.FlashCardViewController;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FlashCardViewController flashCardViewController;

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
        displayBox.setHgrow(displayBox.getChildren().get(1), Priority.ALWAYS);
    }

}
