package es.gonzo.springboot.league.app.exceptions;

import java.util.UUID;

public class BidNotFoundException extends RuntimeException {

    public BidNotFoundException(UUID idPlayer, UUID idUserBid, UUID idCommunity, String seasonId) {
        super(String.format("Bid for idPlayer=%s done by idUserBid=%s [idCommunity=%s and season=%s] not found", idPlayer, idUserBid, idCommunity, seasonId));
    }
}
