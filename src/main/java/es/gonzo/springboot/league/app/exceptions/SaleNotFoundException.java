package es.gonzo.springboot.league.app.exceptions;

public class SaleNotFoundException extends RuntimeException {

    public SaleNotFoundException(Long idSale) {
        super(String.format("Sale %s not found", idSale));
    }

    public SaleNotFoundException(Long idPlayer, Long idUserOwner, Long idCommunity, String seasonId) {
        super(String.format("Sale for idPlayer=%s owner by idUserOwner=%s [idCommunity=%s and season=%s] not found", idPlayer, idUserOwner, idCommunity, seasonId));
    }
}
