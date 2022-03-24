package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.dao.BidRepository;
import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.exceptions.BidNotFoundException;
import es.gonzo.springboot.league.app.exceptions.SaleNotFoundException;
import es.gonzo.springboot.league.app.models.BidJoin;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final SaleRepository saleRepository;

    @Override
    public BidJoin fetchBidByPlayerIdAndIdUserBidAndCommunityAndSeason(Long idPlayer, Long idUserBid, Long idCommunity, String seasonId) {
        return bidRepository.findByPlayerIdAndIdUserBidAndCommunityAndSeason(idPlayer, idUserBid, idCommunity, seasonId)
                .orElseThrow(() -> new BidNotFoundException(idPlayer, idUserBid, idCommunity, seasonId));
    }

    @Override
    public Set<BidJoin> fetchBidListByIdUserBidAndCommunityAndSeason(Long idUserBid, Long idCommunity, String seasonId) {
        return bidRepository.findByIdUserBidAndCommunityAndSeason(idUserBid, idCommunity, seasonId);
    }

    @Override
    public Set<BidJoin> fetchPendingBidListByIdUserBidAndCommunityAndSeason(Long idUserBid, Long idCommunity, String seasonId) {
        return bidRepository.findByIdUserBidAndIdCommunityAndSeasonAndStatus(idUserBid, idCommunity, seasonId, TransactionStatus.PENDING);
    }

    @Override
    @Transactional
    public void createBidBy(Long idSale, Long idUserBid, BigDecimal amount) {
        final Sale sale = saleRepository.findById(idSale).orElseThrow(() -> new SaleNotFoundException(idSale));
        final Bid newBid = Bid.builder()
                .idUserBid(idUserBid)
                .amount(amount)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        newBid.addSale(sale);
    }
}
