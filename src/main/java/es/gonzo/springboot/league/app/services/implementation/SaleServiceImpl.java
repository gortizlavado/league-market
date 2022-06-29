package es.gonzo.springboot.league.app.services.implementation;

import es.gonzo.springboot.league.app.dao.ChangeOwnerRepository;
import es.gonzo.springboot.league.app.dao.SaleRepository;
import es.gonzo.springboot.league.app.entity.Bid;
import es.gonzo.springboot.league.app.entity.ChangeOwner;
import es.gonzo.springboot.league.app.entity.Sale;
import es.gonzo.springboot.league.app.exceptions.SaleNotFoundException;
import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import es.gonzo.springboot.league.app.services.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ChangeOwnerRepository changeOwnerRepository;

    @Override
    @Transactional(readOnly = true)
    public Sale fetchSalablePlayer(
            UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId) {
        return saleRepository
                .findByIdPlayerAndIdUserOwnerAndIdCommunityAndSeason(
                        idPlayer, idUserOwner, idCommunity, seasonId)
                .orElseThrow(() -> new SaleNotFoundException(idPlayer, idUserOwner, idCommunity, seasonId));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Sale> fetchListOfSalablePlayers(
            UUID idUserOwner, UUID idCommunity, String seasonId) {
        return saleRepository.findByIdUserOwnerAndIdCommunityAndSeason(idUserOwner, idCommunity, seasonId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Sale> fetchListOfSalablePendingPlayers(
            UUID idUserOwner, UUID idCommunity, String seasonId) {
        return saleRepository.findByIdUserOwnerAndIdCommunityAndSeasonAndStatus(idUserOwner, idCommunity, seasonId, TransactionStatus.PENDING);
    }

    @Transactional
    @Override
    public void acceptOneOfferForPlayer(UUID idPlayer, UUID idUserOwner, UUID idCommunity, String seasonId, UUID idUserBid) {
        log.info("[{}] Trying to accept a bid for the player in the community: {}", idPlayer, idCommunity);
        final Sale sale = this.fetchSalablePlayer(idPlayer, idUserOwner, idCommunity, seasonId);
        log.info("[{}] Found the Sale with id: {}", idPlayer, sale.getId());
        sale.setStatus(TransactionStatus.ACCEPTED);
        sale.getBids().forEach(bid -> {
            bid.setStatus(TransactionStatus.REJECTED);
            if (bid.getIdUserBid().equals(idUserBid)) {
                bid.setStatus(TransactionStatus.ACCEPTED);
            }
        });
        saleRepository.save(sale);
        log.info("[{}] For this sale has been rejected {} bids of {}", idPlayer,
                sale.getBids().stream().map(Bid::getStatus).filter(TransactionStatus::isRejected).count(),
                sale.getBids().size());

        log.info("[{}] Saving a record in change-owner", idPlayer);
        changeOwnerRepository.save(ChangeOwner.builder()
                .idPlayer(idPlayer)
                .idUserLastOwner(idUserOwner)
                .idUserNewOwner(idUserBid)
                .idCommunity(idCommunity).build());
        // switch userOwner (cron end of the day)

        // sum team value and subtract funds for new userOwner (cron end of the day)
        // subtract team value and sum funds for older userOwner (cron end of the day)

        // make algorithm to re-calculate player value (cron end of the month) --> save on the player_price
    }

}
