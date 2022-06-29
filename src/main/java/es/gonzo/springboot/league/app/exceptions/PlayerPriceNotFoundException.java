package es.gonzo.springboot.league.app.exceptions;

import es.gonzo.springboot.league.app.entity.composite.PlayerPriceId;

public class PlayerPriceNotFoundException extends RuntimeException {
    public PlayerPriceNotFoundException(PlayerPriceId playerPriceId) {
        super(String.format("Price for idPlayer=%s in the month=%s [season=%s] not found",
                playerPriceId.getIdPlayer(), playerPriceId.getMonth(), playerPriceId.getSeason()));
    }
}
