package es.gonzo.springboot.league.app.stream;

import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.models.BidJoin;
import es.gonzo.springboot.league.app.models.messaging.BidRequest;
import es.gonzo.springboot.league.app.services.BidService;
import es.gonzo.springboot.league.app.services.SaleService;
import es.gonzo.springboot.league.app.support.SupportIntegrationTest;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static es.gonzo.springboot.league.app.config.stream.StreamListenerConfiguration.BID_SINK;
import static es.gonzo.springboot.league.app.config.stream.StreamListenerConfiguration.ROUTING_EVENT_TYPE;

@Import(TestChannelBinderConfiguration.class)
class StreamListenerConfigurationTest extends SupportIntegrationTest {

    @Autowired
    StreamBridge streamBridge;

    @Autowired
    BidService bidService;

    @Autowired
    SaleService saleService;

    @Test
    @FlywayTest(locationsForMigrate = {"loadmsql"}, invokeBaselineDB = true)
    void shouldNoPendingBidAtEnd_whenRequestBidForPlayerAndThenCancelIt() {
        // GIVEN
        UUID userThatCreateTheRequest = UUID.randomUUID();
        UUID idCommunity = UUID.fromString("982adeb4-f3d2-4e56-a558-9e51dfb4e3ae");
        String seasonId = "2021/2022";
        BidRequest.BidRequestBuilder builder = BidRequest.builder()
                .idSale(1L)
                .idUserBid(userThatCreateTheRequest)
                .amount(BigDecimal.valueOf(350L));

        // WHEN
        streamBridge.send(BID_SINK,
                MessageBuilder.withPayload(builder.build())
                        .setHeader(ROUTING_EVENT_TYPE, "createConsumer")
                        .build());

        // THEN
        Set<BidJoin> createBidJoins = bidService.fetchBidListByIdUserBidAndCommunityAndSeason(userThatCreateTheRequest, idCommunity, seasonId);
        Assertions.assertEquals(1, createBidJoins.size());

        BidJoin next = createBidJoins.iterator().next();
        Sale sale = saleService.fetchSalablePlayer(next.getIdPlayer(), next.getIdCommunity(), seasonId);
        Assertions.assertEquals(2, sale.getBids().size());

        // WHEN
        streamBridge.send(BID_SINK,
                MessageBuilder.withPayload(builder.idBid(2L).build())
                        .setHeader(ROUTING_EVENT_TYPE, "cancelConsumer")
                        .build());

        // THEN
        Set<BidJoin> pendingBidJoins = bidService.fetchPendingBidListByIdUserBidAndCommunityAndSeason(userThatCreateTheRequest, idCommunity, seasonId);
        Assertions.assertTrue(pendingBidJoins.isEmpty());
    }

}