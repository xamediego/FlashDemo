package com.example.demo;

import mai.flash.FlashDemo;
import mai.flash.domain.Card;
import mai.flash.domain.Deck;
import mai.flash.events.deldeckevent.DeleteDeckEvent;
import mai.flash.events.importevent.importEvent;
import mai.flash.logic.Scheduler;
import mai.flash.repositories.DeckRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = FlashDemo.class)
class DemoApplicationTests {

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private ApplicationContext context;

    @Test
    public void testImport(){

        String filePath = "C:\\Users\\Maizi\\Documents\\demo\\FlashDemo\\testdata\\TestImportFile.txt";
        context.publishEvent(new importEvent(filePath, "testAbleDeck"));

        assertTrue(deckRepository.existsByName("testAbleDeck"));

        context.publishEvent(new DeleteDeckEvent(deckRepository.findByName("testAbleDeck").getId()));
    }

    @Test
    public void testDelete(){
        Deck deleteTestDeck = new Deck();
        deckRepository.save(deleteTestDeck);

        context.publishEvent(new DeleteDeckEvent(deleteTestDeck.getId()));
        assertTrue(!deckRepository.existsByName("testAbleDeck"));
    }


    @Test
    public void SMTest(){
        Card test1 = new Card("Alpha");
        test1.setCardInterval(50);
        Card test2 = new Card("Beta");
        test2.setCardInterval(40);
        Card test3 = new Card("Ceta");
        test3.setCardInterval(30);
        Card test4 = new Card("Delta");
        test4.setCardInterval(20);
        Card test5 = new Card("Epsilon");
        test5.setCardInterval(2);
        Card test6 = new Card("Gamma");
        test6.setCardInterval(1);

        List<Card> testCardList = new ArrayList<>();
        testCardList.add(test1);
        testCardList.add(test2);
        testCardList.add(test3);
        testCardList.add(test4);
        testCardList.add(test5);
        testCardList.add(test6);
        testCardList.forEach(c -> c.setReviewDate(Date.valueOf(LocalDate.now())));

        System.out.println("Hard");
        testCardList.forEach(c -> c.setReviewDate(Scheduler.getNewSchedule(c,"Hard")));
        System.out.println("\nGood:");
        testCardList.forEach(c -> c.setReviewDate(Scheduler.getNewSchedule(c,"Good")));
        System.out.println("\nEasy:");
        testCardList.forEach(c -> c.setReviewDate(Scheduler.getNewSchedule(c,"Easy")));

    }

}
