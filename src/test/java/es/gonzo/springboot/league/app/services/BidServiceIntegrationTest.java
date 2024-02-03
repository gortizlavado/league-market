package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.dao.BidRepository;
import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.messaging.BidRequest;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import es.gonzo.springboot.league.app.support.SupportIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class BidServiceIntegrationTest extends SupportIntegrationTest {

    @Autowired
    BidService service;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    SaleRepository saleRepository;

    @Test
    void shouldSaveBid_whenExistOneSale() {

        final UUID idPlayer = UUID.randomUUID();
        final UUID idUserBid = UUID.randomUUID();
        final UUID idCommunity = UUID.randomUUID();
        final String season = "2021/2022";
        var newSale = Sale.builder()
                .idPlayer(idPlayer)
                .idUserOwner(UUID.randomUUID())
                .idCommunity(idCommunity)
                .season(season)
                .bidAmount(BigDecimal.TEN)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDate.now()).build();
        final Sale saleSaved = saleRepository.save(newSale);
        final long idSale = saleSaved.getId();

        service.createBidBy(MessageBuilder.withPayload(BidRequest.builder()
                .idSale(idSale)
                .idUserBid(idUserBid)
                .amount(BigDecimal.valueOf(350L)).build()).build());

        Assertions.assertEquals(1, service.fetchBidListByIdUserBidAndCommunityAndSeason(idUserBid, idCommunity, season).size());
        Assertions.assertNotNull(service.fetchBidByPlayerIdAndIdUserBidAndCommunityAndSeason(idPlayer, idUserBid, idCommunity, season));
        Assertions.assertEquals(1, saleRepository.findById(idSale).orElseThrow().getBids().size());
    }
}