package mai.flash.repositories;

import mai.flash.domain.CardEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardEntryRepository extends CrudRepository<CardEntry,Long> {

    List<CardEntry> findByCard_Id(Long id);

    List<CardEntry> findByDeckGroup_Id(Long id);



    @Query("select c from CardEntry c where c.card.id = ?1")
    CardEntry getEntryInstanceByCardId(Long id);



}
