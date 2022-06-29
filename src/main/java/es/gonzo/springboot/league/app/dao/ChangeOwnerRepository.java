package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.ChangeOwner;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ChangeOwnerRepository extends CrudRepository<ChangeOwner, Long> {

    Iterable<ChangeOwner> findByIdCommunityAndChangeDateEquals(UUID idCommunity, LocalDate localDate);
}
