package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        id = "PAY123";
        method = "Voucher Code";
        status = "Pending";
        paymentData = Map.of("voucherCode", "VOUCHER123");
    }

    @Test
    void testValidPaymentInitialization() {
        Payment payment = new Payment(id, method, status, paymentData);

        assertEquals(id, payment.getId());
        assertEquals(method, payment.getMethod());
        assertEquals(status, payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testValidVoucherCodeSubFeature() {
        Payment payment = new Payment(id, "Voucher Code", status, Map.of("voucherCode", "NEWVOUCHER"));

        assertEquals("Voucher Code", payment.getMethod());
        assertTrue(payment.getPaymentData().containsKey("voucherCode"));
        assertEquals("NEWVOUCHER", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testValidCashOnDeliverySubFeature() {
        Payment payment = new Payment("PAY456", "Cash on Delivery", "Pending", Map.of("deliveryAddress", "123 Main St"));

        assertEquals("Cash on Delivery", payment.getMethod());
        assertTrue(payment.getPaymentData().containsKey("deliveryAddress"));
        assertEquals("123 Main St", payment.getPaymentData().get("deliveryAddress"));
    }

    @Test
    void testValidStatusUpdate() {
        Payment payment = new Payment(id, method, status, paymentData);
        payment.setStatus("Completed");

        assertEquals("Completed", payment.getStatus());
    }

    @Test
    void testInvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment(null, method, status, paymentData);
        });

        assertEquals("ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment(id, "", status, paymentData);
        });

        assertEquals("Method cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidStatus() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment(id, method, null, paymentData);
        });

        assertEquals("Status cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidPaymentData() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment(id, method, status, null);
        });

        assertEquals("Payment data cannot be null", exception.getMessage());
    }

    @Test
    void testMissingVoucherCodeInPaymentData() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment(id, "Voucher Code", status, Map.of());
        });

        assertEquals("Voucher code is required for Voucher Code payment", exception.getMessage());
    }

    @Test
    void testMissingDeliveryAddressInPaymentData() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment(id, "Cash on Delivery", status, Map.of());
        });

        assertEquals("Delivery address is required for Cash on Delivery", exception.getMessage());
    }
}