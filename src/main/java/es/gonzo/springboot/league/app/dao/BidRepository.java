package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.models.BidJoin;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BidRepository extends CrudRepository<Bid, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT new es.gonzo.springboot.league.app.models.BidJoin(b.id, b.idUserBid, b.amount, b.status, s.idPlayer, s.idCommunity, s.season, b.createdAt) FROM Bid b INNER JOIN b.sale s WHERE s.idPlayer = ?1 AND s.idCommunity = ?3 AND s.season = ?4 AND b.idUserBid = ?2")
    Optional<BidJoin> findByPlayerIdAndIdUserBidAndCommunityAndSeason(UUID idPlayer, UUID idUserBid, UUID idCommunity, String seasonId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT new es.gonzo.springboot.league.app.models.BidJoin(b.id, b.idUserBid, b.amount, b.status, s.idPlayer, s.idCommunity, s.season, b.createdAt) FROM Bid b INNER JOIN b.sale s WHERE s.idCommunity = ?2 AND s.season = ?3 AND b.idUserBid = ?1")
    Set<BidJoin> findByIdUserBidAndCommunityAndSeason(UUID idUserBid, UUID idCommunity, String seasonId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT new es.gonzo.springboot.league.app.models.BidJoin(b.id, b.idUserBid, b.amount, b.status, s.idPlayer, s.idCommunity, s.season, b.createdAt) FROM Bid b INNER JOIN b.sale s WHERE s.idCommunity = ?2 AND s.season = ?3 AND b.status = ?4 AND b.idUserBid = ?1")
    Set<BidJoin> findByIdUserBidAndIdCommunityAndSeasonAndStatus(UUID idUserBid, UUID idCommunity, String seasonId, TransactionStatus status);
}
