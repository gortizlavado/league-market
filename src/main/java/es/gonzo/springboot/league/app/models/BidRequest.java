package es.gonzo.springboot.league.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidRequest {

    private Long idSale;

    private Long idBid;

    private UUID idUserBid;

    private BigDecimal amount;
}
