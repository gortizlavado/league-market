package es.gonzo.springboot.league.app.entity;

import es.gonzo.springboot.league.app.entity.composite.PlayerPriceId;
import es.gonzo.springboot.league.app.models.enums.Month;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@IdClass(PlayerPriceId.class)
@Table(name = "player_price")
public class PlayerPrice {

    @Id
    @Column(name = "player_id")
    private UUID idPlayer;

    @Id
    @Column(name = "season_id")
    private String season;

    @Id
    private Month month;

    private BigDecimal price;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlayerPrice that = (PlayerPrice) o;
        return idPlayer != null && Objects.equals(idPlayer, that.idPlayer)
                && season != null && Objects.equals(season, that.season)
                && month != null && Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlayer, season, month);
    }
}
