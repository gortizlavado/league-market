package es.gonzo.springboot.league.app.dao;

import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.BidJoin;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class BidRepositoryTest {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    BidRepository bidRepository;

    @Test
    void shouldBeOneRecord_whenPutPlayerInSale() {
        var newSale = Sale.builder()
                .idPlayer(27L)
                .idUserOwner(20L)
                .idCommunity(1L)
                .season("2021/2022")
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();

        saleRepository.save(newSale);

        Assertions.assertEquals(1, saleRepository.count());
    }

    @Test
    void shouldGetBid_whenUserRequestForPlayerBidStatus() {
        var newSale = Sale.builder()
                .idPlayer(27L)
                .idUserOwner(20L)
                .idCommunity(1L)
                .season("2021/2022")
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();

        saleRepository.save(newSale);
        Assertions.assertEquals(1, saleRepository.count());

        final Optional<Sale> saleFounded = saleRepository.findByIdPlayerAndIdUserOwnerAndIdCommunityAndSeason(27L, 20L, 1L, "2021/2022");
        Assertions.assertTrue(saleFounded.isPresent());
        final Sale sale = saleFounded.get();

        sale.addBid(Bid.builder()
                .idUserBid(12L)
                .amount(BigDecimal.ONE)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now()).build());
        saleRepository.save(sale);

        Assertions.assertEquals(1, bidRepository.count());
        final Optional<Sale> saleFoundedAgain = saleRepository.findByIdPlayerAndIdUserOwnerAndIdCommunityAndSeason(27L, 20L, 1L, "2021/2022");
        Assertions.assertEquals(1, saleFoundedAgain.get().getBids().size());

        final Optional<BidJoin> optionalBid = bidRepository.findByPlayerIdAndIdUserBidAndCommunityAndSeason(27L, 12L, 1L, "2021/2022");
        Assertions.assertTrue(optionalBid.isPresent());
    }

    @Test
    void shouldGetListOfBids_whenUserRequestAllBidsStatus() {
        var newSaleOne = Sale.builder()
                .idPlayer(25L)
                .idUserOwner(20L)
                .idCommunity(1L)
                .season("2021/2022")
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();

        final Set<Bid> bidSetOne = Set.of(
                Bid.builder()
                        .idUserBid(12L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(13L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now().plusMinutes(3L)).build());
        for (Bid bid : bidSetOne) {
            newSaleOne.addBid(bid);
        }

        var newSaleTwo = Sale.builder()
                .idPlayer(15L)
                .idUserOwner(2L)
                .idCommunity(1L)
                .season("2021/2022")
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();

        final Set<Bid> bidSetTwo = Set.of(
                Bid.builder()
                        .idUserBid(12L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(13L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now().plusMinutes(3L)).build());
        for (Bid bid : bidSetTwo) {
            newSaleTwo.addBid(bid);
        }

        var newSaleThree = Sale.builder()
                .idPlayer(55L)
                .idUserOwner(20L)
                .idCommunity(2L)
                .season("2021/2022")
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();

        final Set<Bid> bidSetThree = Set.of(
                Bid.builder()
                        .idUserBid(2L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now()).build(),
                Bid.builder()
                        .idUserBid(3L)
                        .amount(BigDecimal.ONE)
                        .status(TransactionStatus.PENDING)
                        .createdAt(LocalDateTime.now().plusMinutes(3L)).build());
        for (Bid bid : bidSetThree) {
            newSaleThree.addBid(bid);
        }
        saleRepository.saveAll(List.of(newSaleOne, newSaleTwo, newSaleThree));

        Assertions.assertEquals(6, ((List<Bid>) bidRepository.findAll()).size());
        Assertions.assertEquals(2, bidRepository.findByIdUserBidAndCommunityAndSeason(12L, 1L, "2021/2022").size());

        Assertions.assertEquals(2, bidRepository.findByIdUserBidAndIdCommunityAndSeasonAndStatus(12L, 1L, "2021/2022", TransactionStatus.PENDING).size());
        Assertions.assertEquals(0, bidRepository.findByIdUserBidAndIdCommunityAndSeasonAndStatus(12L, 1L, "2021/2022", TransactionStatus.REJECTED).size());
    }

}
