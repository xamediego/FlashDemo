package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import mai.flash.logic.ImportFile;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TextField newDeckName;

    private FileChooser fileChooser = new FileChooser();

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;

    private ImportFile importer;

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
        importer = new ImportFile();
        importer.startImport(filePath.getText(), newDeckName.getText());
        saveNewDeck();
    }

    private void saveNewDeck(){
        deckRepository.save(importer.getNewDeck());
        cardRepository.saveAll(importer.getSaveAbleCardList());
        cardEntryRepository.saveAll(importer.getSaveAbleCardEntryList());
    }


}
