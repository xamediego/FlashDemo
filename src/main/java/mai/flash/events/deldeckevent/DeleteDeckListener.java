package mai.flash.events.deldeckevent;

import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckGroupRepository;
import mai.flash.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DeleteDeckListener implements ApplicationListener<DeleteDeckEvent> {

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private DeckGroupRepository deckGroupRepository;

    //orphan removal suddenly stopped working so temp use have to delete it like this
    @Override
    public void onApplicationEvent(DeleteDeckEvent deleteDeckEvent) {
        cardRepository.deleteByDeck_Id(deleteDeckEvent.getDeckId());
        deckGroupRepository.deleteByDeck_Id(deleteDeckEvent.getDeckId());
        deckRepository.delete(deckRepository.findById(deleteDeckEvent.getDeckId()).get());
    }

}
