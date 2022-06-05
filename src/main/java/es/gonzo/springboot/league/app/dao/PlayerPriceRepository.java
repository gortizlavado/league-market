package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.PlayerPrice;
import es.gonzo.springboot.league.app.entity.composite.PlayerPriceId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface PlayerPriceRepository extends CrudRepository<PlayerPrice, PlayerPriceId> {

    @Query("select u.price from PlayerPrice u where u.id = ?1")
    Optional<BigDecimal> fetchPriceById(PlayerPriceId playerPriceId);

    Iterable<PlayerPrice> findBySeasonAndIdPlayer(String season, UUID idPlayer);
}
