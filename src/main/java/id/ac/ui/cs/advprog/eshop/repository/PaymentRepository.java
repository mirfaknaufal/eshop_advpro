package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    public Payment save(Payment payment) {
        if (payment.getId() == null || payment.getId().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        Optional<Payment> existingPayment = payments.stream()
                .filter(p -> p.getId().equals(payment.getId()))
                .findFirst();

        if (existingPayment.isPresent()) {
            int index = payments.indexOf(existingPayment.get());
            payments.set(index, payment);
            return payment;
        } else {
            if (payments.isEmpty() || payments.stream().noneMatch(p -> p.getId().equals(payment.getId()))) {
                payments.add(payment);
                return payment;
            } else {
                throw new IllegalArgumentException("Cannot update a non-existent payment");
            }
        }
    }

    public Payment findById(String id) {
        return payments.stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments);
    }
}