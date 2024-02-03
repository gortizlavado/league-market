package es.gonzo.springboot.league.app.models.messaging;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeOwnerRequest {

    private String idPlayer;
    private String idUserLastOwner;
    private String idUserNewOwner;
    private String idCommunity;
}
