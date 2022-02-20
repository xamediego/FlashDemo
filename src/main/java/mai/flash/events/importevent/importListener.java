package mai.flash.events.importevent;

import mai.flash.domain.Card;
import mai.flash.domain.CardEntry;
import mai.flash.domain.Deck;
import mai.flash.domain.DeckGroup;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckGroupRepository;
import mai.flash.repositories.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class importListener implements ApplicationListener<importEvent> {

    private List<Card> saveAbleCardList;
    private List<CardEntry> saveAbleCardEntryList;
    private List<DeckGroup> groupList;

    private Deck deck;

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;
    @Autowired
    private DeckGroupRepository deckGroupRepository;

    @Override
    public void onApplicationEvent(importEvent importEvent) {
        saveAbleCardList = new ArrayList<>();
        saveAbleCardEntryList = new ArrayList<>();

        createDeck(importEvent.getDeckName());

        parseRows(importEvent.getFilePath());
    }

    private void createDeck(String deckName) {
        deck = new Deck(deckName);
    }

    private void parseRows(String filePath) {
        try {
            Files.lines(Paths.get(filePath)).map((row -> row.split("\\t"))).forEach(line -> prepareInput(line));
            saveNewDeck();
        } catch (Exception e) {
            System.out.println("Something went wrong while reading the file" +
                    "\n" + e);
        }
    }

    private void prepareInput(String[] line) {

        List<CardEntry> tempEntryList = prepareEntryList(line);
        Card newCard = prepareCard(line[0], tempEntryList);

        tempEntryList.forEach(e -> e.setCard(newCard));

        addToSaveAble(newCard, tempEntryList);
    }

    private void addToSaveAble(Card newCard, List<CardEntry> entryList) {
        saveAbleCardList.add(newCard);
        entryList.forEach(entry -> saveAbleCardEntryList.add(entry));
    }

    private Card prepareCard(String keyword, List<CardEntry> entryList) {
        return new Card(keyword, entryList, "new", deck);
    }

    //ignores the first value which is reserved for the card key
    private List<CardEntry> prepareEntryList(String[] line) {
        List<CardEntry> cardEntryList = new ArrayList<>();

        for (int i = 1; i < line.length; i++) {
            cardEntryList.add(new CardEntry(line[i]));
        }
        return cardEntryList;
    }

    private void groupCards() {
        groupList = new ArrayList<>();

        DeckGroup newGroup = new DeckGroup(deck);

        for (int i = 1; i <= saveAbleCardList.size(); i++) {

            newGroup.getCardList().add(saveAbleCardList.get(i - 1));

            if (i % 20 == 0) {
                newGroup.setGroupNumber(groupList.size() + 1);
                groupList.add(newGroup);
                newGroup = new DeckGroup(deck);
            }

        }

        groupList.forEach(g -> deck.getGroupList().add(g));
        groupList.forEach(g -> g.getCardList().forEach(c -> c.getCardEntryList().forEach(entry -> entry.setDeckGroup(g))));

    }

    private void saveNewDeck() {

        deckRepository.save(deck);

        groupCards();

        cardRepository.saveAll(saveAbleCardList);
        deckGroupRepository.saveAll(groupList);

        cardEntryRepository.saveAll(saveAbleCardEntryList);

        //grouper();

        System.out.println("New Deck Size: " + deckRepository.count() +
                "\nNew Card a Size: " + cardRepository.count() +
                "\nNew Entry a Size: " + cardEntryRepository.count());
    }

}
