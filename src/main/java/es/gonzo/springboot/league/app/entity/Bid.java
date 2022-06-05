package es.gonzo.springboot.league.app.entity;

import es.gonzo.springboot.league.app.models.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bid")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_bid_id")
    private UUID idUserBid;

    @Column(name = "bid_amount")
    private BigDecimal amount;

    @Column(name = "bid_status")
    @Enumerated(EnumType.ORDINAL)
    private TransactionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

    public void addSale(Sale sale) {
        this.sale = sale;
        sale.addBid(this);
    }

    // toString throw a StackOverflowError, so I generated toString.
    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", idUserBid=" + idUserBid +
                ", amount=" + amount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (!Objects.equals(id, bid.id)) return false;
        if (!Objects.equals(idUserBid, bid.idUserBid)) return false;
        if (!Objects.equals(amount, bid.amount)) return false;
        if (status != bid.status) return false;
        return Objects.equals(createdAt, bid.createdAt);
    }

    // auto-generated hashCode throw a StackOverflowError, so I generated manually.
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idUserBid != null ? idUserBid.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
