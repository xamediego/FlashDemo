package mai.flash.Controllers.subcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import mai.flash.Controllers.BadValueStorageClass;
import mai.flash.domain.Card;
import mai.flash.logic.Reviewer;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
public class GroupFlashViewController implements Initializable {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private DeckRepository deckRepository;

    private List<Card> currentCardList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void goodAnswer() {
        if(!currentCardList.get(0).isGoodLabel()){
            currentCardList.get(0).setGoodLabel(true);
            currentCardList.add(currentCardList.remove(0));
        }else{
            Reviewer.goodCard(currentCardList.get(0), deckRepository.findById(BadValueStorageClass.getSelectedDeckId()).get());
        }
    }

    @FXML
    private void hardAnswer() {

        if(!currentCardList.get(0).isHardLabel()){
            currentCardList.get(0).setHardLabel(true);
            currentCardList.get(0).setEasinessFactor(currentCardList.get(0).getEasinessFactor() * deckRepository.findById(BadValueStorageClass.getSelectedDeckId()).get().getPunishmentFactor());
        }else{
            currentCardList.add(5, currentCardList.remove(0));
        }

    }

    @FXML
    private void easyAnswer() {
        Reviewer.easyCard(currentCardList.get(0), deckRepository.findById(BadValueStorageClass.getSelectedDeckId()).get());
    }
}
