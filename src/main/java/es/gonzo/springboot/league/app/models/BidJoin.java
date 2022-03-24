package es.gonzo.springboot.league.app.models;

import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidJoin {

    private Long id;

    private Long idUserBid;

    private BigDecimal amount;

    private TransactionStatus status;

    private Long idPlayer;

    private Long idCommunity;

    private String season;

    private LocalDateTime createdAt;

}
