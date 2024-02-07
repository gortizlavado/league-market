package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

    @Transactional(readOnly = true)
    Optional<Sale> findByIdPlayerAndIdUserOwnerAndIdCommunityAndSeason(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String season);

    @Transactional(readOnly = true)
    Optional<Sale> findByIdPlayerAndIdCommunityAndSeason(UUID idPlayer, UUID idCommunity, String season);

    @Transactional(readOnly = true)
    Set<Sale> findByIdUserOwnerAndIdCommunityAndSeason(UUID idUserOwner, UUID idCommunity, String season);

    @Transactional(readOnly = true)
    Set<Sale> findByIdUserOwnerAndIdCommunityAndSeasonAndStatus(UUID idUserOwner, UUID idCommunity, String season, TransactionStatus status);
}
