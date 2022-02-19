package mai.flash.Controllers.subcontrollers;

import com.google.common.io.Files;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import mai.flash.events.importevent.importEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.io.File;

/*
import is currently set to be seperated by tab, might add other functions later
 */

@Component
public class ImportScreenController {

    @FXML
    private Label filePath;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private TextField newDeckName;

    private FileChooser fileChooser = new FileChooser();

    private final ApplicationContext applicationContext;

    public ImportScreenController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    private void selectFilePath(){

        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            filePath.setText(selectedFile.getAbsolutePath());
            newDeckName.setText(Files.getNameWithoutExtension(selectedFile.getName()));
        }
    }

    @FXML
    private void cancelImport(){
        resetFilePath();
    }

    private void resetFilePath(){
        filePath.setText(null);
    }

    private void resetDeckNameField(){
        newDeckName.setText(null);
    }

    /*
    want to move the save to the ImportFile class but autowiring the repos doesnt seem to work there
     */
    @FXML
    private void importFile(){
        if(!newDeckName.getText().isEmpty()) {
            nameErrorLabel.setText(null);
            applicationContext.publishEvent(new importEvent(filePath.getText(), newDeckName.getText()));

            resetFilePath();
            resetDeckNameField();
        }else{
            nameErrorLabel.setText("Please enter a valid Deck Name");
        }
    }

}
