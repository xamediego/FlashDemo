package mai.flash.repositories;

import mai.flash.domain.Card;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Date;
import java.util.List;

public interface CardRepository extends CrudRepository<Card,Long> {



    List<Card> findByDeck_IdAndCardStatus(Long id, String cardStatus);

    @Query("select c from Card c where c.deck.id = ?1 and c.cardStatus = ?2")
    List<Card> FindPageable_IdAndCardStatus(Long id, String cardStatus, Pageable pageable);

    List<Card> findByDeck_IdAndCardStatusAndReviewDateIsLessThanEqual(Long id, String cardStatus, java.sql.Date reviewDate);

    List<Card> findByDeck_IdAndReviewDateIsLessThanEqual(Long id, java.sql.Date reviewDate);

}
