package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Builder
public class Payment {
    private String id;
    private String method;

    @Setter
    private String status;

    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        validateId(id);
        validateMethod(method);
        validatePaymentData(paymentData);

        this.id = id;
        this.method = method;
        this.status = "PENDING";
        this.paymentData = paymentData;

        validatePaymentDataForMethod(method, paymentData);
    }

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        validateId(id);
        validateMethod(method);
        validatePaymentData(paymentData);

        this.id = id;
        this.method = method;
        this.status = (status == null || status.trim().isEmpty()) ? "PENDING" : status;
        this.paymentData = paymentData;

        validatePaymentDataForMethod(method, paymentData);
    }

    private void validateId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
    }

    private void validateMethod(String method) {
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Method cannot be null or empty");
        }
    }

    private void validatePaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }
    }

    private void validatePaymentDataForMethod(String method, Map<String, String> paymentData) {
        if ("Voucher Code".equals(method) && !paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Voucher code is required for Voucher Code payment");
        }
        if ("Cash on Delivery".equals(method) && !paymentData.containsKey("deliveryAddress")) {
            throw new IllegalArgumentException("Delivery address is required for Cash on Delivery");
        }
    }
}