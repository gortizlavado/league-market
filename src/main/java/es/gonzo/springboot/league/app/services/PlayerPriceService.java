package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.entity.composite.PlayerPriceId;

import java.math.BigDecimal;

public interface PlayerPriceService {

    BigDecimal fetchPriceBy(PlayerPriceId playerPriceId);
}
