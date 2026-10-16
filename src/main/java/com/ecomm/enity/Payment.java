package com.ecomm.enity;

import com.ecomm.enity.enums.PaymentMethod;
import com.ecomm.enity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String transactionId;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Builder
    public Payment(Long id, BigDecimal amount, PaymentMethod paymentMethod,
                   PaymentStatus status, String transactionId, LocalDateTime paidAt,
                   LocalDateTime createdAt, Order order) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.transactionId = transactionId;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
        this.order = order;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if(this.status == null) {
            this.status = PaymentStatus.PENDING;
        }
    }
}
