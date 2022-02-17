package mai.flash.repositories;

import mai.flash.domain.CardEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardEntryRepository extends CrudRepository<CardEntry,Long> {

    List<CardEntry> findByCard_Id(Long id);

}
