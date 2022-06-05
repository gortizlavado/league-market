package es.gonzo.springboot.league.app.entity;

import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id")
    private UUID idPlayer;

    @Column(name = "user_owner_id")
    private UUID idUserOwner;

    @Column(name = "community_id")
    private UUID idCommunity;

    @Column(name = "season_id")
    private String season;

    @Column(name = "initial_bid_amount")
    private BigDecimal bidAmount;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public void addBid(Bid bid) {
        if (null == this.bids) {
            this.bids = new HashSet<>();
        }
        this.bids.add(bid);
        bid.setSale(this);
    }

    public void removeBid(Bid bid) {
        this.bids.remove(bid);
        bid.setSale(null);
    }
}
