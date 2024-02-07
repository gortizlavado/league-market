package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.dao.BidRepository;
import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.BidJoin;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import es.gonzo.springboot.league.app.support.SupportIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

class SaleServiceIntegrationTest extends SupportIntegrationTest {

    @Autowired
    SaleService service;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    BidRepository bidRepository;

    @Test
    void shouldBeOneBidAccepted_whenUserAcceptSaleForOnePlayer() {

        final UUID idPlayer = UUID.randomUUID();
        final UUID idUserOwner = UUID.randomUUID();
        final UUID idUserBidWin = UUID.randomUUID();
        final UUID idCommunity = UUID.randomUUID();
        final String seasonId = "2021/2022";
        final Set<Bid> bidSet = Set.of(
                Bid.builder()
                        .idUserBid(UUID.randomUUID())
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(idUserBidWin)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(UUID.randomUUID())
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(UUID.randomUUID())
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

        service.acceptOneOfferForPlayer(idPlayer, idUserOwner, idCommunity, seasonId, idUserBidWin);

        Assertions.assertEquals(0, service.fetchListOfSalablePendingPlayers(idUserOwner, idCommunity, seasonId).size());
        final Set<Sale> sales = service.fetchListOfSalablePlayers(idUserOwner, idCommunity, seasonId);
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

        final Optional<BidJoin> bidWin = bidRepository.
                findByPlayerIdAndIdUserBidAndCommunityAndSeason(idPlayer, idUserBidWin, idCommunity, seasonId);
        Assertions.assertEquals(TransactionStatus.ACCEPTED, bidWin.orElseThrow().getStatus());
    }
}