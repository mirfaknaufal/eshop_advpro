package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    CANCELLED("CANCELLED");

    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus orderStatus : PaymentStatus.values()) {
            if (orderStatus.value.equals(param)) {
                return true;
            }
        }
        return false;
    }
}