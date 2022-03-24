package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.entity.Sale;

import java.util.Set;

public interface SaleService {

    Sale fetchSaleByPlayerIdAndIdOwnerAndCommunityAndSeason(Long idPlayer, Long idUserOwner, Long idCommunity, String seasonId);

    Set<Sale> fetchSaleListByIdOwnerAndCommunityAndSeason(Long idPlayer, Long idUserOwner, Long idCommunity, String seasonId);

    Set<Sale> fetchPendingSaleListByIdOwnerAndCommunityAndSeason(Long idPlayer, Long idUserOwner, Long idCommunity, String seasonId);

    void acceptOneBidForPlayerFromUserSale(Long idPlayer, Long idUserOwner, Long idCommunity, String seasonId, Long idUserBid);

}
