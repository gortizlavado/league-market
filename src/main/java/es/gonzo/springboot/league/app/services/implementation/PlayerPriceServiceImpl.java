package es.gonzo.springboot.league.app.services.implementation;

import es.gonzo.springboot.league.app.dao.PlayerPriceRepository;
import es.gonzo.springboot.league.app.entity.composite.PlayerPriceId;
import es.gonzo.springboot.league.app.exceptions.PlayerPriceNotFoundException;
import es.gonzo.springboot.league.app.services.PlayerPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerPriceServiceImpl implements PlayerPriceService {

    private final PlayerPriceRepository repository;

    @Override
    public BigDecimal fetchPriceBy(PlayerPriceId playerPriceId) {
        return repository.fetchPriceById(playerPriceId).orElseThrow(() -> new PlayerPriceNotFoundException(playerPriceId));
    }
}
