package es.gonzo.springboot.league.app.services.implementation;

import es.gonzo.springboot.league.app.dao.BidRepository;
import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.exceptions.BidNotFoundException;
import es.gonzo.springboot.league.app.exceptions.SaleNotFoundException;
import es.gonzo.springboot.league.app.models.BidJoin;
import es.gonzo.springboot.league.app.models.BidRequest;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import es.gonzo.springboot.league.app.services.BidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final SaleRepository saleRepository;

    @Override
    @Transactional(readOnly = true)
    public BidJoin fetchBidByPlayerIdAndIdUserBidAndCommunityAndSeason(UUID idPlayer, UUID idUserBid, UUID idCommunity, String seasonId) {
        return bidRepository.findByPlayerIdAndIdUserBidAndCommunityAndSeason(idPlayer, idUserBid, idCommunity, seasonId)
                .orElseThrow(() -> new BidNotFoundException(idPlayer, idUserBid, idCommunity, seasonId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BidJoin> fetchBidListByIdUserBidAndCommunityAndSeason(UUID idUserBid, UUID idCommunity, String seasonId) {
        return bidRepository.findByIdUserBidAndCommunityAndSeason(idUserBid, idCommunity, seasonId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<BidJoin> fetchPendingBidListByIdUserBidAndCommunityAndSeason(UUID idUserBid, UUID idCommunity, String seasonId) {
        return bidRepository.findByIdUserBidAndIdCommunityAndSeasonAndStatus(idUserBid, idCommunity, seasonId, TransactionStatus.PENDING);
    }

    @Override
    @Transactional
    public void createBidBy(Message<BidRequest> bidRequestMessage) {
        log.info("request to create a bid");
        // See https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream-binder-rabbit.html#rabbitmq-stream-consumer
        //Context context = msg.getHeaders().get("rabbitmq_streamContext", Context.class);
        //context.consumer().store(context.offset());

        BidRequest bidRequest = bidRequestMessage.getPayload();
        Long idSale = bidRequest.getIdSale();
        final Sale sale = saleRepository.findById(idSale).orElseThrow(() -> new SaleNotFoundException(idSale));
        final Bid newBid = Bid.builder()
                .idUserBid(bidRequest.getIdUserBid())
                .amount(bidRequest.getAmount())
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        log.info("creating a new row in the bid table");
        newBid.addSale(sale);
    }

    @Override
    @Transactional
    public void cancelBidBy(Message<BidRequest> bidRequestMessage) {
        log.info("request to cancel a bid");
        BidRequest bidRequest = bidRequestMessage.getPayload();
        Long idBid = bidRequest.getIdBid();

        Bid bid = bidRepository.findById(idBid).orElseThrow(() -> new BidNotFoundException(idBid));
        log.info("soft deleting a new row in the bid table");
        bid.setStatus(TransactionStatus.CANCELLED);
        bidRepository.save(bid);
    }
}
