package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.ChangeOwner;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.flyway.OptimizedFlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        OptimizedFlywayTestExecutionListener.class})
@AutoConfigureEmbeddedDatabase
class ChangeOwnerRepositoryTest {

    @Autowired
    ChangeOwnerRepository repository;

    @Test
    @FlywayTest
    void shouldBeOneRecord_whenPutChangeOwnerRecord() {
        var newEntry = ChangeOwner.builder()
                .idPlayer(UUID.randomUUID())
                .idUserLastOwner(UUID.randomUUID())
                .idUserNewOwner(UUID.randomUUID())
                .idCommunity(UUID.randomUUID())
                .build();

        final ChangeOwner changeOwnerSaved = repository.save(newEntry);
        Assertions.assertEquals(1, repository.count());

        final Optional<ChangeOwner> changeOwnerOptional = repository.findById(changeOwnerSaved.getId());
        Assertions.assertNotNull(changeOwnerOptional.orElseThrow().getCreatedAt());
        final LocalDate expectedDate = LocalDate.now().plusDays(1);
        Assertions.assertEquals(expectedDate, changeOwnerOptional.orElseThrow().getChangeDate());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldGetListOfChangesToDo_whenSearchBySeasonAndBeAvailableForToday() {
        final UUID idCommunity = UUID.fromString("982adeb4-f3d2-4e56-a558-9e51dfb4e3ae");

        final Iterable<ChangeOwner> prices =
                repository.findByIdCommunityAndChangeDateEquals(idCommunity, LocalDate.now());
        Assertions.assertTrue(prices.iterator().hasNext());
        Assertions.assertEquals(1, ((List<?>) prices).size());
    }

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldGetEmptyListOfChangesToDo_whenIsNoAvailableForToday() {
        final UUID idCommunity = UUID.fromString("20e9f46c-5323-4305-8fbb-79aa29406828");

        final Iterable<ChangeOwner> prices =
                repository.findByIdCommunityAndChangeDateEquals(idCommunity, LocalDate.now());
        Assertions.assertFalse(prices.iterator().hasNext());
    }

}
