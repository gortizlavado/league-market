package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.models.BidJoin;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public interface BidService {

    BidJoin fetchBidByPlayerIdAndIdUserBidAndCommunityAndSeason(UUID idPlayer, UUID idUserBid, UUID idCommunity, String seasonId);

    Set<BidJoin> fetchBidListByIdUserBidAndCommunityAndSeason(UUID idUserBid, UUID idCommunity, String seasonId);

    Set<BidJoin> fetchPendingBidListByIdUserBidAndCommunityAndSeason(UUID idUserBid, UUID idCommunity, String seasonId);

    void createBidBy(Long idSale, UUID idUserBid, BigDecimal amount);
}
