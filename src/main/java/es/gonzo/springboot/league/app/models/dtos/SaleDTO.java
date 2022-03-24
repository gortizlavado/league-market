package es.gonzo.springboot.league.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDTO {

    private String id;

    private PlayerDTO player;

    private String idUserOwner;

    private String idCommunity;

    private String amount;

    private String status;

    private Set<BidDTO> bids;

    private String season;
}
