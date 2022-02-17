package mai.flash.repositories;

import mai.flash.domain.Deck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;


public interface DeckRepository extends CrudRepository<Deck, Long> {

    @Query("select count(d) from Deck d left join d.cardList cardList where d.name = ?1 and cardList.cardStatus = ?2")
    long getCardStatusSize(String name, String cardStatus);

    @Query("select count(d) from Deck d left join d.cardList cardList where d.name = ?1 and cardList.cardStatus = ?2 and cardList.reviewDate <= ?3")
    long getCardStatusDateLessSize(String name, String cardStatus, Date reviewDate);


}
