package es.gonzo.springboot.league.app.entity.composite;

import es.gonzo.springboot.league.app.models.enums.Month;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//See https://www.baeldung.com/jpa-composite-primary-keys
public class PlayerPriceId implements Serializable {

    private String season;

    private UUID idPlayer;

    private Month month;
}