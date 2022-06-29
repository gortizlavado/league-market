package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.entity.Sale;

import java.util.Set;
import java.util.UUID;

public interface SaleService {

    Sale fetchSalablePlayer(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId);

    Set<Sale> fetchListOfSalablePlayers(UUID idUserOwner, UUID idCommunity, String seasonId);

    Set<Sale> fetchListOfSalablePendingPlayers(UUID idUserOwner, UUID idCommunity, String seasonId);

    void acceptOneOfferForPlayer(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId, UUID idUserBid);

}
