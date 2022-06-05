package es.gonzo.springboot.league.app.controllers;

import es.gonzo.springboot.league.app.models.dtos.PlayerDTO;
import es.gonzo.springboot.league.app.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    @Autowired
    SaleService saleService;

    @GetMapping("/alive")
    @ResponseStatus(HttpStatus.OK)
    public void isAlive() {}

    @GetMapping("/player/{id}")
    public PlayerDTO getPlayerInfo(@PathVariable String id) {
        return PlayerDTO.builder().build();//saleService.getPlayerInfo(id);
    }

    //TODO use soft delete
}
