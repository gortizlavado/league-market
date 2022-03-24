package es.gonzo.springboot.league.app.exceptions;

public class BidNotFoundException extends RuntimeException {

    public BidNotFoundException(Long idPlayer, Long idUserBid, Long idCommunity, String seasonId) {
        super(String.format("Bid for idPlayer=%s done by idUserBid=%s [idCommunity=%s and season=%s] not found", idPlayer, idUserBid, idCommunity, seasonId));
    }
}
