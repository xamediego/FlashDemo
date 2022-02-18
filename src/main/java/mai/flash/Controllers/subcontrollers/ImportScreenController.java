package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    public void selectFilePath(){
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            filePath.setText(selectedFile.getAbsolutePath());
        }
    }

    public void cancelImport(){
        filePath.setText(null);
    }

    /*
    want to move the save to the ImportFile class but autowiring the repos doesnt seem to work there
     */
    public void importFile(){
        if(!newDeckName.getText().isEmpty()) {
            nameErrorLabel.setText(null);
            applicationContext.publishEvent(new importEvent(filePath.getText(), newDeckName.getText()));
        }else{
            nameErrorLabel.setText("Please enter a valid Deck Name");
        }
    }

}
