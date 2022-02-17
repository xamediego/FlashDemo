package mai.flash.repositories;

import mai.flash.domain.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Date;
import java.util.List;

public interface CardRepository extends CrudRepository<Card,Long> {

    List<Card> findByCardStatusAndDeck_Name(String cardStatus, String name);

    @Query("select c from Card c where c.cardStatus = ?1 and c.deck.name = ?2")
    List<Card> getPagedCards(String cardStatus, String name, Pageable pageable);

    List<Card> findByDeck_NameAndReviewDateIsLessThanEqual(String name, Date reviewDate);

    List<Card> findByDeck_NameAndReviewDateIsLessThanEqualAndCardStatus(String name, java.sql.Date reviewDate, String cardStatus);

}
