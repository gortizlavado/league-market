package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.PlayerPrice;
import es.gonzo.springboot.league.app.entity.composite.PlayerPriceId;
import es.gonzo.springboot.league.app.models.enums.Month;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
class PlayerPriceRepositoryTest {

    @Autowired
    PlayerPriceRepository repository;

    @Test
    @FlywayTest
    void shouldBeOneRecord_whenPutPlayerPriceRecord() {
        var newEntry = PlayerPrice.builder()
                .idPlayer(UUID.randomUUID())
                .season("2022/2023")
                .month(Month.February)
                .price(BigDecimal.valueOf(10_000)).build();

        repository.save(newEntry);

        Assertions.assertEquals(1, repository.count());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldGetPrice_whenSearch() {
        final UUID idPlayer = UUID.fromString("4ad8a8f3-2e2a-406e-8ced-470d1a3c00be");
        final String season = "2021/2022";
        final BigDecimal expectedPrice = BigDecimal.valueOf(800);

        final Optional<BigDecimal> price = repository.fetchPriceById(PlayerPriceId.builder()
                .idPlayer(idPlayer)
                .season(season)
                .month(Month.December)
                .build());

        Assertions.assertEquals(expectedPrice, price.orElseThrow());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldGetListOfPrices_whenSearchBySeasonAndPlayer() {
        final UUID idPlayer = UUID.fromString("4ad8a8f3-2e2a-406e-8ced-470d1a3c00be");
        final String season = "2022/2023";

        final Iterable<PlayerPrice> prices = repository.findBySeasonAndIdPlayer(season, idPlayer);
        Assertions.assertTrue(prices.iterator().hasNext());
        Assertions.assertEquals(9, ((List<?>) prices).size());
    }

}