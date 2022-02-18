package mai.flash.events.deldeckevent;

import mai.flash.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DeleteDeckListener implements ApplicationListener<DeleteDeckEvent> {

    @Autowired
    private final DeckRepository deckRepository;

    public DeleteDeckListener(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void onApplicationEvent(DeleteDeckEvent deleteDeckEvent) {
        deckRepository.delete(deckRepository.findById(deleteDeckEvent.getDeckId()).get());
    }

}
