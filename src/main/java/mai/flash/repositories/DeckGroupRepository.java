package mai.flash.repositories;

import mai.flash.domain.DeckGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DeckGroupRepository extends CrudRepository<DeckGroup, Long> {

    List<DeckGroup> findByDeck_Id(Long id);

    DeckGroup findByDeck_Name(String name);

    long deleteByDeck_Id(Long id);






}
