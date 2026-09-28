package com.worldcupticket.ms_sales.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String matchId;
    private String sectorId;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItem(Order order, String matchId, String sectorId, Integer quantity, BigDecimal unitPrice) {
        this.order = order;
        this.matchId = matchId;
        this.sectorId = sectorId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

}
