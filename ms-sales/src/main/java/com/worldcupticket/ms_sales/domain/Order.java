package com.worldcupticket.ms_sales.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.worldcupticket.ms_sales.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matchId;
    private Long userId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private Status status;
    private BigDecimal price;
    private LocalDateTime expiresAt;

    public Order(String matchId, Long userId, List<OrderItem> items, Status status, BigDecimal price) {
        this.matchId = matchId;
        this.userId = userId;
        this.items = items;
        this.status = status;
        this.price = price;
    }

}
