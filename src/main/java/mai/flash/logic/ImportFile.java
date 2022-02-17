package mai.flash.logic;

import mai.flash.domain.Card;
import mai.flash.domain.CardEntry;
import mai.flash.domain.Deck;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImportFile {

    public List<Card> saveAbleCardList = new ArrayList<>();
    public List<CardEntry> saveAbleCardEntryList = new ArrayList<>();
    public Deck deck;

    public void startImport(String filePath, String deckName){
        createDeck(deckName);
        parseRows(filePath);
    }

    private void createDeck(String deckName){
        deck = new Deck(deckName);
    }

    private void parseRows(String filePath){
        try{
            Files.lines(Paths.get(filePath)).map((row -> row.split("\\t"))).forEach(line -> prepareInput(line));
        }catch (Exception e){
            System.out.println(e);
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

    public List<Card> getSaveAbleCardList() {
        return saveAbleCardList;
    }

    public List<CardEntry> getSaveAbleCardEntryList() {
        return saveAbleCardEntryList;
    }

    public Deck getNewDeck() {
        return deck;
    }
}
