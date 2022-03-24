package es.gonzo.springboot.league.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidDTO {

    private String id;

    private String idUserBid;

    private String amount;
}