package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private String orderId;
    private String paymentId;
    private String method;
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderId = "ORDER123";
        paymentId = "PAY123";
        method = "VOUCHER";

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        paymentData.put("orderId", orderId);

        order = spy(new Order(orderId));
        when(orderRepository.findById(orderId)).thenReturn(order);
    }

    @Test
    void testAddPaymentHappyPath() {
        Payment expectedPayment = new Payment(paymentId, method, PaymentStatus.PENDING.getValue(), paymentData);

        when(paymentRepository.save(any(Payment.class))).thenReturn(expectedPayment);

        Payment actualPayment = paymentService.addPayment(order, method, paymentData);

        assertNotNull(actualPayment);
        assertEquals(paymentId, actualPayment.getId());
        assertEquals(method, actualPayment.getMethod());
        assertEquals(PaymentStatus.PENDING.getValue(), actualPayment.getStatus());
        assertEquals(paymentData, actualPayment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentUnhappyPath_NullOrder() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(null, method, paymentData);
        });

        assertEquals("Order cannot be null", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testAddPaymentUnhappyPath_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(order, null, paymentData);
        });

        assertEquals("Payment method cannot be null or empty", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }


    @Test
    void testSetStatusUnhappyPath_InvalidStatus() {
        Payment payment = new Payment(paymentId, method, PaymentStatus.PENDING.getValue(), paymentData);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "INVALID_STATUS");
        });

        assertEquals("Invalid payment status", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }


    @Test
    void testGetPaymentHappyPath() {
        Payment payment = new Payment(paymentId, method, PaymentStatus.PENDING.getValue(), paymentData);

        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        Payment foundPayment = paymentService.getPayment(paymentId);

        assertNotNull(foundPayment);
        assertEquals(paymentId, foundPayment.getId());
        assertEquals(method, foundPayment.getMethod());
        assertEquals(PaymentStatus.PENDING.getValue(), foundPayment.getStatus());
        assertEquals(paymentData, foundPayment.getPaymentData());

        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetPaymentUnhappyPath_NotFound() {
        when(paymentRepository.findById(paymentId)).thenReturn(null);

        Payment foundPayment = paymentService.getPayment(paymentId);

        assertNull(foundPayment);

        verify(paymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void testGetAllPaymentsHappyPath() {
        Payment payment1 = new Payment("PAY123", method, PaymentStatus.PENDING.getValue(), paymentData);
        Payment payment2 = new Payment("PAY456", "Cash on Delivery", PaymentStatus.SUCCESS.getValue(), Map.of("deliveryAddress", "123 Main St"));

        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        List<Payment> allPayments = paymentService.getAllPayments();

        assertNotNull(allPayments);
        assertEquals(2, allPayments.size());
        assertTrue(allPayments.contains(payment1));
        assertTrue(allPayments.contains(payment2));

        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPaymentsUnhappyPath_EmptyList() {
        when(paymentRepository.findAll()).thenReturn(Collections.emptyList());

        List<Payment> allPayments = paymentService.getAllPayments();

        assertNotNull(allPayments);
        assertTrue(allPayments.isEmpty());

        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testSetStatusHappyPath_Success() {
        Payment payment = new Payment(paymentId, method, PaymentStatus.PENDING.getValue(), paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), updatedPayment.getStatus());

        verify(order).setStatus(OrderStatus.SUCCESS.getValue());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusHappyPath_Rejected() {
        Payment payment = new Payment(paymentId, method, PaymentStatus.PENDING.getValue(), paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment updatedPayment = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.REJECTED.getValue(), updatedPayment.getStatus());

        verify(order).setStatus(OrderStatus.FAILED.getValue());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusUnhappyPath_NullPayment() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(null, PaymentStatus.SUCCESS.getValue());
        });

        assertEquals("Payment cannot be null", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testSetStatusUnhappyPath_NullStatus() {
        Payment payment = new Payment(paymentId, method, PaymentStatus.PENDING.getValue(), paymentData);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, null);
        });

        assertEquals("Status cannot be null or empty", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testVoucherPayment_Success() {
        when(order.getId()).thenReturn(UUID.randomUUID().toString());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, method, paymentData);

        assertNotNull(payment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());

        verify(order).setStatus("SUCCESS");
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testVoucherPayment_Rejected() {
        paymentData.put("voucherCode", "INVALID12345678");
        when(order.getId()).thenReturn(UUID.randomUUID().toString());
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, method, paymentData);

        assertNotNull(payment);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());

        verify(order).setStatus("FAILED");
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

}