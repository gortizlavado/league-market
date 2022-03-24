package es.gonzo.springboot.league.app.restClient;

import es.gonzo.springboot.league.app.config.ClientConfiguration;
import es.gonzo.springboot.league.app.models.dtos.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="players-microservice",
        url="http://league-plaer.com/",
        configuration = ClientConfiguration.class)
public interface PlayerClient {

    @GetMapping("/api/players/{id}")
    PlayerDTO getPlayerInfo(@PathVariable String id);
}
