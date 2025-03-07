package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        id = "PAY123";
        method = "Voucher Code";
        status = PaymentStatus.PENDING.getValue();
        paymentData = Map.of("voucherCode", "VOUCHER123");
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment(id, method, status, paymentData);

        Payment savedPayment = paymentRepository.save(payment);

        assertNotNull(savedPayment);
        assertEquals(payment, savedPayment);
    }

    @Test
    void testFindById() {
        Payment payment = new Payment(id, method, status, paymentData);
        paymentRepository.save(payment);

        Optional<Payment> foundPayment = Optional.ofNullable(paymentRepository.findById(id));

        assertTrue(foundPayment.isPresent());
        assertEquals(payment, foundPayment.get());
    }

    @Test
    void testFindAll() {
        Payment payment1 = new Payment("PAY123", "Voucher Code", PaymentStatus.PENDING.getValue(), Map.of("voucherCode", "VOUCHER123"));
        Payment payment2 = new Payment("PAY456", "Cash on Delivery", PaymentStatus.SUCCESS.getValue(), Map.of("deliveryAddress", "123 Main St"));

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();

        assertEquals(2, allPayments.size());
        assertTrue(allPayments.contains(payment1));
        assertTrue(allPayments.contains(payment2));
    }

    @Test
    void testUpdatePayment() {
        Payment payment = new Payment(id, method, PaymentStatus.PENDING.getValue(), paymentData);
        paymentRepository.save(payment);

        Payment updatedPayment = new Payment(id, method, PaymentStatus.SUCCESS.getValue(), paymentData);
        paymentRepository.save(updatedPayment);

        Optional<Payment> foundPayment = Optional.ofNullable(paymentRepository.findById(id));

        assertTrue(foundPayment.isPresent());
        assertEquals(PaymentStatus.SUCCESS.getValue(), foundPayment.get().getStatus());
    }


    @Test
    void testSavePaymentWithNullId() {
        Payment payment = new Payment(null, method, status, paymentData);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.save(payment);
        });

        assertEquals("ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Payment> foundPayment = Optional.ofNullable(paymentRepository.findById("NON_EXISTENT_ID"));

        assertFalse(foundPayment.isPresent());
    }

    @Test
    void testFindAllWhenEmpty() {
        List<Payment> allPayments = paymentRepository.findAll();

        assertTrue(allPayments.isEmpty());
    }

    @Test
    void testUpdateNonExistentPayment() {
        Payment updatedPayment = new Payment("NON_EXISTENT_ID", method, PaymentStatus.SUCCESS.getValue(), paymentData);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.save(updatedPayment);
        });

        assertEquals("Cannot update a non-existent payment", exception.getMessage());
    }
}