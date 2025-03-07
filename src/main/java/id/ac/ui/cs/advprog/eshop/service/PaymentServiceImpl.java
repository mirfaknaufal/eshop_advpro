package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }

        String paymentId = UUID.randomUUID().toString();
        paymentData.put("orderId", order.getId());

        String status = PaymentStatus.PENDING.getValue();

        if ("VOUCHER".equalsIgnoreCase(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP") && voucherCode.replaceAll("[^0-9]", "").length() == 8) {
                status = PaymentStatus.SUCCESS.getValue();
                order.setStatus("SUCCESS");
            } else {
                status = PaymentStatus.REJECTED.getValue();
                order.setStatus("FAILED");
            }
            orderRepository.save(order);
        } else if ("CASH_ON_DELIVERY".equalsIgnoreCase(method)) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");

            if (address == null || address.isEmpty() || deliveryFee == null || deliveryFee.isEmpty()) {
                status = PaymentStatus.REJECTED.getValue();
            } else {
                status = PaymentStatus.SUCCESS.getValue();
            }
        }

        Payment payment = new Payment(paymentId, method, status, paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        if (!PaymentStatus.SUCCESS.getValue().equals(status) && !PaymentStatus.REJECTED.getValue().equals(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }

        payment.setStatus(status);
        String orderId = payment.getPaymentData().get("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            Order order = orderRepository.findById(orderId);
            if (order != null) {
                if (PaymentStatus.SUCCESS.getValue().equals(status)) {
                    order.setStatus("SUCCESS");
                } else {
                    order.setStatus("FAILED");
                }
                orderRepository.save(order);
            }
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be null or empty");
        }
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}