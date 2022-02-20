package com.example.demo;

import mai.flash.objects.StudyGroup;
import mai.flash.FlashDemo;
import mai.flash.domain.Card;
import mai.flash.domain.Deck;
import mai.flash.domain.DeckGroup;
import mai.flash.events.importevent.importEvent;
import mai.flash.repositories.CardEntryRepository;
import mai.flash.repositories.CardRepository;
import mai.flash.repositories.DeckGroupRepository;
import mai.flash.repositories.DeckRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FlashDemo.class)
public class DeckGroupTest {

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardEntryRepository cardEntryRepository;
    @Autowired
    private DeckGroupRepository deckGroupRepository;
    @Autowired
    private ApplicationContext context;

    @Test
    public void groupTest() {

        String filePath = "C:\\Users\\Maizi\\Documents\\demo\\FlashDemo\\testdata\\TestImportFile.txt";
        context.publishEvent(new importEvent(filePath, "testAbleDeck"));

        assertTrue(deckRepository.existsByName("testAbleDeck"));

        assertTrue(deckRepository.count() > 0);
        assertTrue(deckGroupRepository.count() > 0);

        Deck deck = deckRepository.findByName("testAbleDeck");

        List<DeckGroup> testGroupList = deckGroupRepository.findByDeck_Id(deck.getId());

        assertFalse(testGroupList.isEmpty());

        StudyGroup studyGroup = new StudyGroup(
                cardRepository.findByGroup_Id(testGroupList.get(0).getId()), deck, testGroupList.get(0), cardEntryRepository.findByDeckGroup_Id(testGroupList.get(0).getId()));

        studyGroup.getCardList().forEach(c -> studyGroup.getCardEntryList().add(cardEntryRepository.getEntryInstanceByCardId(c.getId())));

        assertEquals(20, studyGroup.getCardList().size());

        studyGroup.easyAnswer();
        System.out.println("Main size: " + studyGroup.getCardList().size());
        assertEquals(19, studyGroup.getCardList().size());
        System.out.println("Finished size: " + studyGroup.getFinishedList());
        assertEquals(1, studyGroup.getFinishedList().size());

        Card card = studyGroup.getCardList().get(0);

        studyGroup.goodAnswer();
        assertEquals(card, studyGroup.getCardList().get(studyGroup.getCardList().size() - 1));

        studyGroup.hardAnswer();

        for (int i = 0; i < 18; i++) {
            studyGroup.goodAnswer();
        }


        for (int i = 0; i < studyGroup.getCardList().size(); i++) {
            Card hardTest = studyGroup.getCardList().get(0);
            studyGroup.hardAnswer();
            assertEquals(hardTest, studyGroup.getCardList().get(6));
        }

        int count = 0;

        while (!studyGroup.getCardList().isEmpty()) {
            studyGroup.goodAnswer();
            count++;
        }

        assertEquals(26, count);
        assertEquals(0, studyGroup.getCardList().size());
        assertEquals(20, studyGroup.getFinishedList().size());

        deckGroupRepository.save(studyGroup.getDeckGroup());
        cardRepository.saveAll(studyGroup.getAllCards());

        cardRepository.deleteAll();
        deckGroupRepository.deleteAll();
        deckRepository.deleteAll();

    }
}
