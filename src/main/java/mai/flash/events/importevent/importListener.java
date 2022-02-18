package mai.flash.events.importevent;

import mai.flash.domain.Card;
import mai.flash.domain.CardEntry;
import mai.flash.domain.Deck;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
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

    private List<Card> saveAbleCardList = new ArrayList<>();
    private List<CardEntry> saveAbleCardEntryList = new ArrayList<>();
    private Deck deck;

    @Autowired
    private final DeckRepository deckRepository;
    @Autowired
    private final CardRepository cardRepository;
    @Autowired
    private final CardEntryRepository cardEntryRepository;

    public importListener(DeckRepository deckRepository, CardRepository cardRepository, CardEntryRepository cardEntryRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.cardEntryRepository = cardEntryRepository;
    }

    @Override
    public void onApplicationEvent(importEvent importEvent) {
        createDeck(importEvent.getDeckName());
        parseRows(importEvent.getFilePath());
    }

    private void createDeck(String deckName){
        deck = new Deck(deckName);
    }

    private void parseRows(String filePath){
        try{
            Files.lines(Paths.get(filePath)).map((row -> row.split("\\t"))).forEach(line -> prepareInput(line));
            saveNewDeck();
        }catch (Exception e){
            System.out.println("Something went wrong while reading the file");
        }
    }

    private void prepareInput(String[] line){
        System.out.println("Amount of parts " + line.length);
        List<CardEntry> tempEntryList = prepareEntryList(line);
        Card newCard = prepareCard(line[0], tempEntryList);

        tempEntryList.forEach(e -> e.setCard(newCard));

        addToSaveAble(newCard, tempEntryList);
    }

    private void addToSaveAble(Card newCard, List<CardEntry> entryList){
        saveAbleCardList.add(newCard);
        entryList.forEach(entry -> saveAbleCardEntryList.add(entry));
    }

    private Card prepareCard(String keyword, List<CardEntry> entryList){
        System.out.println("new Card " + keyword);
        return new Card(keyword,entryList,"new",deck);
    }

    //ignores the first value which is reserved for the card key
    private List<CardEntry> prepareEntryList(String[] line){
        List<CardEntry> cardEntryList = new ArrayList<>();
        for(int i = 1; i < line.length; i++){
            System.out.println("new entry " + line[i]);
            cardEntryList.add(new CardEntry(line[i]));
        }
        return cardEntryList;
    }

    private void saveNewDeck(){

        System.out.println("Starting save");
        deckRepository.save(deck);
        cardRepository.saveAll(saveAbleCardList);
        cardEntryRepository.saveAll(saveAbleCardEntryList);

        System.out.println("New Deck Size: " + deckRepository.count() +
                "\nNew Card a Size: " + cardRepository.count() +
                "\nNew Entry a Size: " + cardEntryRepository.count());
    }
}
