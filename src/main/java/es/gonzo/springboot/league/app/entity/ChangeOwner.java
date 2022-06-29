package es.gonzo.springboot.league.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "change_owner")
public class ChangeOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id")
    private UUID idPlayer;

    @Column(name = "user_last_owner_id")
    private UUID idUserLastOwner;

    @Column(name = "user_new_owner_id")
    private UUID idUserNewOwner;

    @Column(name = "community_id")
    private UUID idCommunity;

    @Column(name = "change_date", insertable = false, updatable = false)
    private LocalDate changeDate;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
