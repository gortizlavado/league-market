package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

    @Transactional(readOnly = true)
    Optional<Sale> findByIdPlayerAndIdUserOwnerAndIdCommunityAndSeason(Long idPlayer, Long idUserOwner, Long idCommunity, String season);

    @Transactional(readOnly = true)
    Set<Sale> findByIdUserOwnerAndIdCommunityAndSeason(Long idUserOwner, Long idCommunity, String season);

    @Transactional(readOnly = true)
    Set<Sale> findByIdUserOwnerAndIdCommunityAndSeasonAndStatus(Long idUserOwner, Long idCommunity, String season, TransactionStatus status);
}
