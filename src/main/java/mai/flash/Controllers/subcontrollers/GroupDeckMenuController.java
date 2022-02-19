package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import mai.flash.view.scene.FxmlParts;
import mai.flash.view.scene.SceneSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GroupDeckMenuController {

    @FXML
    private GridPane menuPane;

    @Autowired
    private SceneSwitcher sceneSwitcher;

    @FXML
    private void selectDeck() throws IOException {
        HBox box = (HBox)this.menuPane.getParent();
        box.getChildren().remove(this.menuPane);

        box.getChildren().add(sceneSwitcher.getNode(FxmlParts.GROUPMENU));
        box.setHgrow(box.getChildren().get(1), Priority.ALWAYS);
    }



}
