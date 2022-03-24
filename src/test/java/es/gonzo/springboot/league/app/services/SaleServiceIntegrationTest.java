package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureEmbeddedDatabase
class SaleServiceIntegrationTest {

    @Autowired
    SaleService service;

    @Autowired
    SaleRepository saleRepository;

    @Test
    void shouldBeOneBidAccepted_whenUserAcceptSaleForOnePlayer() {
        final long userWin = 4L, idPlayer = 27L, idUserOwner = 2L, idCommunity = 10L;
        final String seasonId = "2021/2022";
        final Set<Bid> bidSet = Set.of(
                Bid.builder()
                        .idUserBid(3L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(userWin)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(5L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(6L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now().plusMinutes(3L)).build());
        var newSale = Sale.builder()
                .idPlayer(idPlayer)
                .idUserOwner(idUserOwner)
                .idCommunity(idCommunity)
                .season(seasonId)
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();
        for (Bid bid : bidSet) {
            newSale.addBid(bid);
        }
        saleRepository.save(newSale);

        service.acceptOneBidForPlayerFromUserSale(idPlayer, idUserOwner, idCommunity, seasonId, userWin);

        Assertions.assertEquals(0, service.fetchPendingSaleListByIdOwnerAndCommunityAndSeason(idPlayer, idUserOwner, idCommunity, seasonId).size());
        final Set<Sale> sales = service.fetchSaleListByIdOwnerAndCommunityAndSeason(idPlayer, idUserOwner, idCommunity, seasonId);
        final long acceptedBids = sales.stream()
                .map(Sale::getBids)
                .flatMap(bids -> bids.stream().map(Bid::getStatus))
                .filter(TransactionStatus::isAccepted).count();
        Assertions.assertEquals(1, acceptedBids);

        final long rejectedBids = sales.stream()
                .map(Sale::getBids)
                .flatMap(bids -> bids.stream().map(Bid::getStatus))
                .filter(TransactionStatus::isRejected).count();

        Assertions.assertEquals(3, rejectedBids);
    }
}