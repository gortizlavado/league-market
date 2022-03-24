package es.gonzo.springboot.league.app.models.enums;

public enum TransactionStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    CANCELLED,
    EXPIRED;

    public static boolean isPending(TransactionStatus status) {
       return PENDING == status;
    }

    public static boolean isAccepted(TransactionStatus status) {
        return ACCEPTED == status;
    }

    public static boolean isRejected(TransactionStatus status) {
        return REJECTED == status;
    }
}
