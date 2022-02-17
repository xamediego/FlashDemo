package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mai.flash.domain.Card;
import mai.flash.domain.CardEntry;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChangeCardController implements Initializable {

    @FXML
    private MenuButton dropMenu;
    @FXML
    private TextField keyInput;
    @FXML
    private TextField entityInput;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardEntryRepository cardEntryRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckRepository.findAll().forEach(d -> dropMenu.getItems().add(new MenuItem(d.getName())));
        dropMenu.setText(dropMenu.getItems().get(0).getText());
    }

    @FXML
    private void addNewCard(){
        saveCard();
        keyInput.setText(null);
        entityInput.setText(null);
    }


/*
next 3 function could be done better?
 */
    private void saveCard(){
        Card newCard = prepareCard();
        List<CardEntry> inputList = prepareCardEntity();
        inputList.forEach(e -> e.setCard(newCard));

        cardRepository.save(newCard);
        cardEntryRepository.saveAll(inputList);
    }

    private Card prepareCard(){
        return new Card(keyInput.getText(),prepareCardEntity(),"new",deckRepository.findByName(dropMenu.getText()));
    }

    private List<CardEntry> prepareCardEntity() {
        List<CardEntry> entryList = new ArrayList<>();
        entryList.add(new CardEntry(entityInput.getText()));
        return entryList;
    }

}
