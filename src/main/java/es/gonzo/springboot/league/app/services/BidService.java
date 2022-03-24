package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.models.BidJoin;

import java.math.BigDecimal;
import java.util.Set;

public interface BidService {

    BidJoin fetchBidByPlayerIdAndIdUserBidAndCommunityAndSeason(Long idPlayer, Long idUserBid, Long idCommunity, String seasonId);

    Set<BidJoin> fetchBidListByIdUserBidAndCommunityAndSeason(Long idUserBid, Long idCommunity, String seasonId);

    Set<BidJoin> fetchPendingBidListByIdUserBidAndCommunityAndSeason(Long idUserBid, Long idCommunity, String seasonId);

    void createBidBy(Long idSale, Long idUserBid, BigDecimal amount);
}
