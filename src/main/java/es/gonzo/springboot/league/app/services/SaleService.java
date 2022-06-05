package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.entity.Sale;

import java.util.Set;
import java.util.UUID;

public interface SaleService {

    Sale fetchSaleByPlayerIdAndIdOwnerAndCommunityAndSeason(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId);

    Set<Sale> fetchSaleListByIdOwnerAndCommunityAndSeason(UUID idUserOwner, UUID idCommunity, String seasonId);

    Set<Sale> fetchPendingSaleListByIdOwnerAndCommunityAndSeason(UUID idUserOwner, UUID idCommunity, String seasonId);

    void acceptOneBidForPlayerFromUserSale(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId, UUID idUserBid);

}
