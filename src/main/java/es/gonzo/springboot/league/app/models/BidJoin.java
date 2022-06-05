package es.gonzo.springboot.league.app.models;

import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidJoin {

    private Long id;

    private UUID idUserBid;

    private BigDecimal amount;

    private TransactionStatus status;

    private UUID idPlayer;

    private UUID idCommunity;

    private String season;

    private LocalDateTime createdAt;

}
