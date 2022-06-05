package es.gonzo.springboot.league.app.services;

import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.exceptions.SaleNotFoundException;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    @Override
    public Sale fetchSaleByPlayerIdAndIdOwnerAndCommunityAndSeason(
            UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId) {
        return saleRepository
                .findByIdPlayerAndIdUserOwnerAndIdCommunityAndSeason(
                        idPlayer, idUserOwner, idCommunity, seasonId)
                .orElseThrow(() -> new SaleNotFoundException(idPlayer, idUserOwner, idCommunity, seasonId));
    }

    @Override
    public Set<Sale> fetchSaleListByIdOwnerAndCommunityAndSeason(
            UUID idUserOwner, UUID idCommunity, String seasonId) {
        return saleRepository.findByIdUserOwnerAndIdCommunityAndSeason(idUserOwner, idCommunity, seasonId);
    }

    @Override
    public Set<Sale> fetchPendingSaleListByIdOwnerAndCommunityAndSeason(
            UUID idUserOwner, UUID idCommunity, String seasonId) {
        return saleRepository.findByIdUserOwnerAndIdCommunityAndSeasonAndStatus(idUserOwner, idCommunity, seasonId, TransactionStatus.PENDING);
    }

    @Transactional
    @Override
    public void acceptOneBidForPlayerFromUserSale(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId, UUID idUserBid) {
        final Sale sale = this.fetchSaleByPlayerIdAndIdOwnerAndCommunityAndSeason(idPlayer, idUserOwner, idCommunity, seasonId);
        sale.setStatus(TransactionStatus.ACCEPTED);
        sale.getBids().forEach(bid -> {
            bid.setStatus(TransactionStatus.REJECTED);
            if (bid.getIdUserBid().equals(idUserBid)) {
                bid.setStatus(TransactionStatus.ACCEPTED);
            }
        });
        saleRepository.save(sale);
        // switch userOwner (cron?)
        // sum team value and subtract funds for new userOwner
        // subtract team value and sum funds for older userOwner
        // make algorithm to re-calculate player value
    }

}
