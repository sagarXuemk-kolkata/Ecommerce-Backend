package com.ecomm.enity;

import com.ecomm.enity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    private BigDecimal totalAmount;
    private String notes;
    private LocalDateTime cratedAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private Address shippingAddress;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Payment payment;


    @Builder
    public Order(Long id, String orderNumber, OrderStatus status, BigDecimal totalAmount,
                 String notes, LocalDateTime cratedAt, LocalDateTime updatedAt, User user,
                 Address shippingAddress) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status != null ? status : OrderStatus.PENDING;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.cratedAt = cratedAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.shippingAddress = shippingAddress;
    }

    @PrePersist
    protected void onCreate(){
        this.cratedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if(this.orderNumber == null) {
            this.orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        if(this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    // ----------------- Convenience Method --------------------

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    public void recalculateTotal() {
        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
