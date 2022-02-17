package mai.flash.repositories;

import mai.flash.domain.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends CrudRepository<Card,Long> {


    List<Card> findByDeck_NameAndReviewDate(String name,Date reviewDate);

    List<Card> findByCardStatusAndDeck_Name(String cardStatus, String name);

    List<Card> findByDeck_NameAndReviewDateAndCardStatus(String name, Date reviewDate, String cardStatus);

    @Query("select c from Card c where c.cardStatus = ?1 and c.deck.name = ?2")
    List<Card> getPagedCards(String cardStatus, String name, Pageable pageable);

    List<Card> findByDeck_NameAndReviewDateIsLessThanEqual(String name, Date reviewDate);

    List<Card> findByDeck_NameAndReviewDateIsLessThanEqualAndCardStatus(String name, java.sql.Date reviewDate, String cardStatus);



}
